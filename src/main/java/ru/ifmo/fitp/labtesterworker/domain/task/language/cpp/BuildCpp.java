package ru.ifmo.fitp.labtesterworker.domain.task.language.cpp;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.language.cpp.BuildCppDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.CommandTask;

import java.io.File;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.SOLUTION_REPOSITORY_DIR_STORAGE_KEY;

public class BuildCpp extends CommandTask {

    private static Logger LOG = Logger.getLogger(BuildCpp.class);

    public BuildCpp(BuildCppDAO dao) {
        super(dao);
    }

    @Override
    public void perform() {
        LOG.info("Build program");

        processRunner.startProcess((File) storage.get(SOLUTION_REPOSITORY_DIR_STORAGE_KEY));
    }
}
