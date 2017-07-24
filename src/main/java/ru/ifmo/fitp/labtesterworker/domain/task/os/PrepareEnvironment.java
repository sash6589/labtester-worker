package ru.ifmo.fitp.labtesterworker.domain.task.os;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.ENVIRONMENT_DIR_NAME_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.ENVIRONMENT_DIR_STORAGE_KEY;

public class PrepareEnvironment extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(PrepareEnvironment.class);

    @Override
    public void perform() {

        LOG.info("Prepare environment");

        createDirectory();
        fillStorage();
    }

    private void createDirectory() {
        File dir = Environment.ENVIRONMENT_DIR;
        if (!dir.exists()) {
            boolean res = dir.mkdir();
            if (!res) {
                LOG.error("Cannot create a directory");
            }
        }
    }

    private void fillStorage() {
        storage.put(ENVIRONMENT_DIR_STORAGE_KEY, Environment.ENVIRONMENT_DIR);
        storage.put(ENVIRONMENT_DIR_NAME_STORAGE_KEY, Environment.ENVIRONMENT_DIR_NAME);
    }
}
