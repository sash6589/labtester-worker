package ru.ifmo.fitp.labtesterworker.dao.task.language.cpp;

import ru.ifmo.fitp.labtesterworker.dao.task.CommandTaskDAO;

public class BuildCppDAO extends CommandTaskDAO {

    public BuildCppDAO(String command) {
        super(command);
    }

    public BuildCppDAO(String... command) {
        super(command);
    }

    public BuildCppDAO() {
    }
}
