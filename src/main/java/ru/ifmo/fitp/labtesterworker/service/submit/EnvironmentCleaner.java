package ru.ifmo.fitp.labtesterworker.service.submit;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class EnvironmentCleaner {

    private static final Logger LOG = Logger.getLogger(EnvironmentCleaner.class);

    private static final List<String> EXCLUDED_FILES = Collections.singletonList("input.txt");

    public void clean() {

        LOG.info("Cleaning environment");

        File envDir = Environment.ENVIRONMENT_DIR;
        File[] files = envDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!EXCLUDED_FILES.contains(file.getName())) {
                    file.delete();
                }
            }
        } else {
            LOG.error(envDir.getAbsolutePath() + " isn't directory");
        }
    }

}
