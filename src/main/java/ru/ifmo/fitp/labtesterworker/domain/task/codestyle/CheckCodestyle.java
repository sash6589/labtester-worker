package ru.ifmo.fitp.labtesterworker.domain.task.codestyle;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.codestyle.CheckCodestyleDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.CommandTask;

import java.io.File;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;

public class CheckCodestyle extends CommandTask {

    private static Logger LOG = Logger.getLogger(CheckCodestyle.class);

    public CheckCodestyle(CheckCodestyleDAO dao) {
        super(dao);
    }

    @Override
    public void perform() {
        LOG.info("Check codestyle");

        processRunner.startProcess((File) storage.get(WORKING_DIR_STORAGE_KEY));

        fillStorage();
    }

    private void fillStorage() {
        storage.put(CODESTYLE_STDOUT_STORAGE_KEY, processRunner.getStdout());
        storage.put(CODESTYLE_STDERR_STORAGE_KEY, processRunner.getStderr());
    }
}
