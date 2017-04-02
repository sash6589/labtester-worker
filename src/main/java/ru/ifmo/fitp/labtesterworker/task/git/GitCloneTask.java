package ru.ifmo.fitp.labtesterworker.task.git;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.task.AbstractTask;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GitCloneTask extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(GitCloneTask.class);

    private String gitUrl;

    public GitCloneTask(Map<String, Object> storage, String gitUrl) {
        super(storage);
        this.gitUrl = gitUrl;
    }

    @Override
    public void perform() {

        LOG.info("Clone repository");

        cloneRepository();
        fillStorage();
    }

    private void cloneRepository() {
        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", gitUrl);
        processBuilder.directory((File) storage.get("environment-dir"));

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void fillStorage() {
        File dir = getRepositoryDir();

        try {
            LOG.info("Repository directory is " + dir.getCanonicalPath());
            storage.put("repository-dir", dir);
            storage.put("repository-dir-name", dir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getRepositoryDir() {
        File envDir = (File) storage.get("environment-dir");
        File[] files = envDir.listFiles();
        if (files == null) {
            LOG.error("Environment directory is empty");
            return envDir;
        } else {
            return files[0];
        }
    }
}
