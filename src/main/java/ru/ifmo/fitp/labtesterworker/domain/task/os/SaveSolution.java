package ru.ifmo.fitp.labtesterworker.domain.task.os;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.os.SaveSolutionDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;

public class SaveSolution extends AbstractTask {

    private static final Logger LOG = Logger.getLogger(SaveSolution.class);

    private static final String DIR_NAME = "program";
    private static final String FILE_NAME_PATTERN = "Main.";

    private String program;
    private String fileName;

    public SaveSolution(SaveSolutionDAO dao) {
        this.program = dao.getProgram();
        this.fileName = FILE_NAME_PATTERN + dao.getExtension();
    }

    @Override
    public void perform() {
        LOG.info(String.format("Save program to %s/%s", DIR_NAME, fileName));

        try {
            File dir = createDir();
            FileUtils.writeStringToFile(new File(dir, fileName), program, (String) null);
            fillStorage(dir);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UncheckedIOException(e);
        }
    }

    private File createDir() {
        File workingDir = (File) storage.get(WORKING_DIR_STORAGE_KEY);

        File dir = new File(workingDir, DIR_NAME);

        if (dir.mkdir()) {
            return dir;
        } else {
            LOG.error("Cannot create directory for saving program to file");
            return workingDir;
        }
    }

    private void fillStorage(File dir) throws IOException {
        storage.put(SOLUTION_REPOSITORY_DIR_STORAGE_KEY, dir);
        storage.put(EXECUTABLE_DIR_STORAGE_KEY, dir);
        storage.put(SOLUTION_REPOSITORY_DIR_NAME_STORAGE_KEY, dir.getCanonicalPath());
    }
}
