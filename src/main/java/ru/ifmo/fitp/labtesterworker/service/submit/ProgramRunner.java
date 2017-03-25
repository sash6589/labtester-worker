package ru.ifmo.fitp.labtesterworker.service.submit;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.IOException;

public class ProgramRunner {

    private static final Logger LOG = Logger.getLogger(ProgramRunner.class);

    private String programName;

    public ProgramRunner(String programName) {
        this.programName = programName;
    }

    public void runProgram() {

        LOG.info("Run program " + programName);

        ProcessBuilder processBuilder = new ProcessBuilder("python", programName);
        processBuilder.directory(Environment.ENVIRONMENT_DIR);

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        LOG.info(String.format("Program %s finished", programName));
    }
}
