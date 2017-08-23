package ru.ifmo.fitp.labtesterworker.domain.task.os;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.util.Environment;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.WORKING_DIR_STORAGE_KEY;

public class CleanEnvironment extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(CleanEnvironment.class);

    @Override
    public void perform() {
        LOG.info("Cleaning environment");

        File workingDir = (File) storage.get(WORKING_DIR_STORAGE_KEY);
        try {
            FileUtils.deleteDirectory(workingDir);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}
