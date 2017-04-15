package ru.ifmo.fitp.labtesterworker.task;

import ru.ifmo.fitp.labtesterworker.util.ProcessRunner;

import java.util.List;

public abstract class CommandTask extends AbstractTask {

    protected ProcessRunner processRunner;

    public CommandTask(List<String> command) {
        this.processRunner = new ProcessRunner(command);
    }
}
