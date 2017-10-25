package ru.ifmo.fitp.labtesterworker.dao.task.git;

import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneDAO;

public class GitCloneTestsDAO extends GitCloneDAO {

    public GitCloneTestsDAO() {

    }

    public GitCloneTestsDAO(String gitUrl) {
        super(gitUrl);
    }
}
