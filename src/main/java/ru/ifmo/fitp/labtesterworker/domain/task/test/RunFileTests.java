package ru.ifmo.fitp.labtesterworker.domain.task.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.dao.task.RunFileTestsDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.CommandTask;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.EXECUTABLE_DIR_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.FILE_TESTS_DIR_STORAGE_KEY;
import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.FILE_TESTS_RESULT_STORAGE_KEY;

public class RunFileTests extends CommandTask {

    private static Logger LOG = Logger.getLogger(RunFileTests.class);

    private int failed;
    private int passed;

    public RunFileTests(RunFileTestsDAO dao) {
        super(dao);
        this.failed = 0;
        this.passed = 0;
    }

    @Override
    public void perform() {
        LOG.info("Run file tests");

        runTests();

        String report = String.format("Passed: %s, Failed: %s", passed, failed);
        storage.put(FILE_TESTS_RESULT_STORAGE_KEY, report);
        LOG.info(report);
    }

    private void runTests() {
        File dir = (File) storage.get(FILE_TESTS_DIR_STORAGE_KEY);
        Iterator<File> it = FileUtils.iterateFiles(dir, new String[]{"a"}, false);
        while (it.hasNext()) {
            File answer = it.next();
            File input = new File(dir, FilenameUtils.removeExtension(answer.getName()));
            String name = input.getName();
            try {
                runTest(name, input, answer);
            } catch (IOException e) {
                LOG.info(e.getMessage());
                incFailed(name);
            }
        }
    }

    private void runTest(String name, File input, File answer) throws IOException {
        File runDir = (File) storage.get(EXECUTABLE_DIR_STORAGE_KEY);

        File destSrc = new File(runDir, "input.txt");
        FileUtils.copyFile(input, destSrc);

        boolean result = processRunner.startProcessDefaultTimeout((File) storage.get(EXECUTABLE_DIR_STORAGE_KEY));
        if (!result) {
            incFailed(name);
            return;
        }

        File output = new File(runDir, "output.txt");
        checkOutput(name, output, answer);
    }

    private void checkOutput(String name, File output, File answer) throws IOException {
        if (!output.exists()) {
            incFailed(name);
            return;
        }

        if (FileUtils.contentEqualsIgnoreEOL(output, answer, null)) {
            incPassed(name);
        } else {
            incFailed(name);
        }
    }

    private void incFailed(String name) {
        LOG.info("Test " + name + " failed");
        ++failed;
    }

    private void incPassed(String name) {
        LOG.info("Test " + name + " passed");
        ++passed;
    }
}
