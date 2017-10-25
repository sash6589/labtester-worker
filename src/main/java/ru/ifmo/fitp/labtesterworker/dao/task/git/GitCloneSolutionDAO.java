package ru.ifmo.fitp.labtesterworker.dao.task.git;

import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneDAO;

public class GitCloneSolutionDAO extends GitCloneDAO {

    public GitCloneSolutionDAO() {}

    public GitCloneSolutionDAO(String gitUrl) {
        super(gitUrl);
    }
}
