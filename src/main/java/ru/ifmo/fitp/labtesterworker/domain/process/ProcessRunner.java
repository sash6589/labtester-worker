package ru.ifmo.fitp.labtesterworker.domain.process;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProcessRunner {

    private static final Logger LOG = Logger.getLogger(ProcessRunner.class);

    private String command;
    private ProcessConfiguration processConfiguration;

    private String stdout;
    private String stderr;

    public ProcessRunner(List<String> command) {
        this.command = String.join(" ", command);
        this.processConfiguration = new ProcessConfiguration(command);
    }

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public boolean startProcess(File dir, long timeout) {
        boolean result = true;

        try {
            Process process = processConfiguration.startProcess(dir);

            result = process.waitFor(timeout, TimeUnit.SECONDS);

            if (result) {
                int exitValue = process.exitValue();
                LOG.info(String.format("Command '%s' finished with exit code %s", command, exitValue));

                stdout = captureOutput(process.getInputStream());
                stderr = captureOutput(process.getErrorStream());
            } else {
                LOG.info(String.format("Command '%s' terminated due to timeout", command));
                process.destroyForcibly();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void startProcess(File dir) {
        startProcess(dir, 100500);
    }

    public boolean startProcessDefaultTimeout(File dir) {
        return startProcess(dir, 2);
    }

    private String captureOutput(InputStream inputStream) {
        StringBuilder processOutput = new StringBuilder();

        try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = processOutputReader.readLine()) != null) {
                processOutput.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processOutput.toString();
    }
}
