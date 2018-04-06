package ru.ifmo.fitp.labtesterworker.dao.task.language;

import ru.ifmo.fitp.labtesterworker.dao.task.CommandTaskDAO;

public class BuildDAO extends CommandTaskDAO {

    public BuildDAO(String command) {
        super(command);
    }

    public BuildDAO(String... command) {
        super(command);
    }

    public BuildDAO() {
    }
}
