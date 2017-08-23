package ru.ifmo.fitp.labtesterworker.domain.task.os;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;

public class PrepareEnvironment extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(PrepareEnvironment.class);

    @Override
    public void perform() {
        LOG.info("Prepare environment");

        try {
            File workingDir = createDirectory();
            fillStorage(workingDir);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    private File createDirectory() throws IOException {

        File dir = Environment.ENVIRONMENT_DIR;
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                LOG.error("Cannot create a environment directory");
                throw new IOException();
            }
        }

        File workingDir = Files.createTempDirectory(dir.toPath(), "tmp").toFile();
        LOG.info("Working directory is " + workingDir.getCanonicalPath());

        return workingDir;
    }

    private void fillStorage(File workingDir) throws IOException {
        storage.put(ENVIRONMENT_DIR_STORAGE_KEY, Environment.ENVIRONMENT_DIR);
        storage.put(ENVIRONMENT_DIR_NAME_STORAGE_KEY, Environment.ENVIRONMENT_DIR_NAME);
        storage.put(WORKING_DIR_STORAGE_KEY, workingDir);
        storage.put(WORKING_DIR_NAME_STORAGE_KEY, workingDir.getCanonicalPath());
    }
}
