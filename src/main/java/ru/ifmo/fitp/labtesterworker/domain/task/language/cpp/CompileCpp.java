package ru.ifmo.fitp.labtesterworker.domain.task.language.cpp;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.language.cpp.CompileCppDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.CommandTask;

import java.io.File;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.SOLUTION_REPOSITORY_DIR_STORAGE_KEY;

public class CompileCpp extends CommandTask {

    private static Logger LOG = Logger.getLogger(CompileCpp.class);

    public CompileCpp(CompileCppDAO dao) {
        super(dao);
    }

    @Override
    public void perform() {
        LOG.info("Compile program");

        processRunner.startProcess((File) storage.get(SOLUTION_REPOSITORY_DIR_STORAGE_KEY));
    }
}
