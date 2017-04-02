package ru.ifmo.fitp.labtesterworker.task.os;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;
import java.util.Map;

public class PrepareEnvironmentTask extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(PrepareEnvironmentTask.class);

    public PrepareEnvironmentTask(Map<String, Object> storage) {
        super(storage);
    }

    @Override
    public void perform() {

        LOG.info("Prepare environment");

        createDirectory();
        fillStorage();
    }

    private void createDirectory() {
        File dir = Environment.ENVIRONMENT_DIR;
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void fillStorage() {
        storage.put("environment-dir", Environment.ENVIRONMENT_DIR);
        storage.put("environment-dir-name", Environment.ENVIRONMENT_DIR_NAME);
    }
}
