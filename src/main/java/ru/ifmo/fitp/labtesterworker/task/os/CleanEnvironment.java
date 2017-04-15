package ru.ifmo.fitp.labtesterworker.task.os;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CleanEnvironment extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(CleanEnvironment.class);

    private static final List<String> EXCLUDED_FILES = Collections.singletonList("test");

    @Override
    public void perform() {

        LOG.info("Cleaning environment");

        File envDir = Environment.ENVIRONMENT_DIR;
        File[] files = envDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!EXCLUDED_FILES.contains(file.getName())) {
                    FileUtils.deleteQuietly(file);
                }
            }
        } else {
            LOG.error(envDir.getAbsolutePath() + " isn't directory");
        }
    }
}
