package ru.ifmo.fitp.labtesterworker.domain.task;

import ru.ifmo.fitp.labtesterworker.process.ProcessRunner;

import java.util.Arrays;

public abstract class CommandTask extends AbstractTask {

    protected ProcessRunner processRunner;

    public CommandTask(String command) {
        this.processRunner = new ProcessRunner(Arrays.asList(command.split(" ")));
    }
}
