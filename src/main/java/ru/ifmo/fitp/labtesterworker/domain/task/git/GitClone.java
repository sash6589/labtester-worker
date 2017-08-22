package ru.ifmo.fitp.labtesterworker.domain.task.git;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.GitCloneDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.CommandTask;

import java.io.File;
import java.io.IOException;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.ENVIRONMENT_DIR_STORAGE_KEY;

public class GitClone extends CommandTask {

    private static final Logger LOG = Logger.getLogger(GitClone.class);

    private String gitUrl;
    private String repositoryDirStorageKey;
    private String repositoryDirNameStorageKey;

    public GitClone(GitCloneDAO dao, String repositoryDirStorageKey, String repositoryDirNameStorageKey) {
        super("git clone " + dao.getGitUrl());
        this.gitUrl = dao.getGitUrl();
        this.repositoryDirStorageKey = repositoryDirStorageKey;
        this.repositoryDirNameStorageKey = repositoryDirNameStorageKey;
    }

    @Override
    public void perform() {
        LOG.info("Clone repository from " + gitUrl);

        processRunner.startProcess();

        fillStorage();
    }

    private void fillStorage() {
        File dir = getRepositoryDir();

        try {
            LOG.info("Repository directory is " + dir.getCanonicalPath());
            storage.put(repositoryDirStorageKey, dir);
            storage.put(repositoryDirNameStorageKey, dir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getRepositoryDir() {
        File envDir = (File) storage.get(ENVIRONMENT_DIR_STORAGE_KEY);
        File[] files = envDir.listFiles();
        if (files == null) {
            LOG.error("Environment directory is empty");
            return envDir;
        }

        return files[0];
    }
}
