package ru.ifmo.fitp.labtesterworker.task.test;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.RunTestsDAO;
import ru.ifmo.fitp.labtesterworker.task.CommandTask;

import java.util.Arrays;

import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.TESTS_RUN_STDERR_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.TESTS_RUN_STDOUT_STORAGE_KEY;

public class RunTests extends CommandTask {

    private static Logger LOG = Logger.getLogger(RunTests.class);

    public RunTests(RunTestsDAO dao) {
        super(Arrays.asList(dao.getCommand().split(" ")));
    }

    @Override
    public void perform() {
        LOG.info("Run tests");

        processRunner.startProcess();

        fillStorage();
    }

    private void fillStorage() {
        storage.put(TESTS_RUN_STDOUT_STORAGE_KEY, processRunner.getStdout());
        storage.put(TESTS_RUN_STDERR_STORAGE_KEY, processRunner.getStderr());
    }
}
