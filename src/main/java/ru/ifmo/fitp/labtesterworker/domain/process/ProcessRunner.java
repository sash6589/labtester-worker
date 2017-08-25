package ru.ifmo.fitp.labtesterworker.domain.process;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

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

    public void startProcess(File dir) {
        try {
            Process process = processConfiguration.startProcess(dir);

            stdout = captureOutput(process.getInputStream());
            stderr = captureOutput(process.getErrorStream());

            process.waitFor();

            int exitValue = process.exitValue();
            String message = "Command '" + command + "' finished with exit code " + exitValue;
            LOG.info(message);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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
