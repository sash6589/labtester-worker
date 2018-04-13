package ru.ifmo.fitp.labtesterworker.domain.task.os;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;

public class MoveTo extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(MoveTo.class);

    private String fromKey;
    private String toKey;
    private String toNameKey;
    private String toPath;

    public MoveTo() {
        this.fromKey = SOLUTION_REPOSITORY_DIR_STORAGE_KEY;
        this.toKey = EXECUTABLE_DIR_STORAGE_KEY;
        this.toNameKey = EXECUTABLE_DIR_NAME_STORAGE_KEY;
        this.toPath = "run";
    }

    public MoveTo(String fromKey, String toKey, String toNameKey, String toPath) {
        this.fromKey = fromKey;
        this.toKey = toKey;
        this.toNameKey = toNameKey;
        this.toPath = toPath;
    }

    @Override
    public void perform() {

        File toDir = null;
        try {
            File fromDir = (File) storage.get(fromKey);
            toDir = new File(((File) storage.get(WORKING_DIR_STORAGE_KEY)).getCanonicalPath() +
                    File.separator + toPath);

            LOG.info(String.format("Move files from %s to %s", fromDir.getCanonicalPath(), toDir.getCanonicalPath()));

            FileUtils.copyDirectory(fromDir, toDir);
//            FileUtils.moveDirectory(fromDir, toDir);
        } catch (FileExistsException e) {
            LOG.info("Nothing to move: " + e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
//            throw new UncheckedIOException(e);
        } finally {
            fillStorage(toDir);
        }
    }

    private void fillStorage(File toDir) {
        try {
            storage.put(toKey, toDir);
            storage.put(toNameKey, toDir.getCanonicalPath());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}
