package ru.ifmo.fitp.labtesterworker.domain.task;

import ru.ifmo.fitp.labtesterworker.dao.task.CommandTaskDAO;
import ru.ifmo.fitp.labtesterworker.domain.process.ProcessRunner;

import java.util.Arrays;
import java.util.List;

public abstract class CommandTask extends AbstractTask {

    protected ProcessRunner processRunner;

    public CommandTask(String command) {
        this.processRunner = new ProcessRunner(Arrays.asList(command.split(" ")));
    }

    public CommandTask(List<String> command) {
        this.processRunner = new ProcessRunner(command);
    }

    public CommandTask(CommandTaskDAO taskDAO) {
        this.processRunner = new ProcessRunner(taskDAO.getCommand());
    }
}
