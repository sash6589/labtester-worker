package ru.ifmo.fitp.labtesterworker.task.os;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.task.AbstractTask;

import java.io.File;
import java.io.IOException;

import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.*;

public class MoveTo extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(MoveTo.class);

    private String fromKey;
    private String toKey;
    private String toNameKey;
    private String toPath;

    public MoveTo() {
        this.fromKey = REPOSITORY_DIR_STORAGE_KEY;
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

        try {
            File fromDir = (File) storage.get(fromKey);
            File toDir = new File(((File) storage.get(ENVIRONMENT_DIR_STORAGE_KEY)).getCanonicalPath() +
                    File.separator + toPath);

            LOG.info(String.format("Move files from %s to %s", fromDir.getCanonicalPath(), toDir.getCanonicalPath()));

            FileUtils.moveDirectory(fromDir, toDir);

            fillStorage(toDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillStorage(File toDir) throws IOException {
        storage.put(toKey, toDir);
        storage.put(toNameKey, toDir.getCanonicalPath());
    }
}
