package ru.ifmo.fitp.labtesterworker.task.git;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.GitCloneDAO;
import ru.ifmo.fitp.labtesterworker.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.ProcessConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.ENVIRONMENT_DIR_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.REPOSITORY_DIR_NAME_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.REPOSITORY_DIR_STORAGE_KEY;

public class GitClone extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(GitClone.class);

    private String gitUrl;

    public GitClone(GitCloneDAO dao) {
        this.gitUrl = dao.getGitUrl();
    }

    @Override
    public void perform() {

        LOG.info("Clone repository");

        cloneRepository();
        fillStorage();
    }

    private void cloneRepository() {
        try {
            Process process = new ProcessConfiguration((File) storage.get(ENVIRONMENT_DIR_STORAGE_KEY),
                    Arrays.asList("git", "clone", gitUrl)).startProcess();
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void fillStorage() {
        File dir = getRepositoryDir();

        try {
            LOG.info("Repository directory is " + dir.getCanonicalPath());
            storage.put(REPOSITORY_DIR_STORAGE_KEY, dir);
            storage.put(REPOSITORY_DIR_NAME_STORAGE_KEY, dir.getCanonicalPath());
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
        } else {
            return files[0];
        }
    }
}
