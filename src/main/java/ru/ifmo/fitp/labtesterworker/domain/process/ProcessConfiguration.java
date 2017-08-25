package ru.ifmo.fitp.labtesterworker.domain.process;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ru.ifmo.fitp.labtesterworker.util.Environment.ENVIRONMENT_DIR;

public class ProcessConfiguration {

    private ProcessBuilder processBuilder;

    public ProcessConfiguration(List<String> command) {
        this.processBuilder = new ProcessBuilder(command);
        this.processBuilder.directory(ENVIRONMENT_DIR);
    }

    public ProcessConfiguration(File directory, List<String> command) {
        this.processBuilder = new ProcessBuilder(command);
        this.processBuilder.directory(directory);
    }

    public Process startProcess(File dir) throws IOException {
        return processBuilder.directory(dir).start();
    }
}
