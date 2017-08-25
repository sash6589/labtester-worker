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
            try {
                runTest(input, answer);
            } catch (IOException e) {
                LOG.info(e.getMessage());
                ++failed;
            }
        }
    }

    private void runTest(File input, File answer) throws IOException {
        File runDir = (File) storage.get(EXECUTABLE_DIR_STORAGE_KEY);

        File destSrc = new File(runDir, "input.txt");
        FileUtils.copyFile(input, destSrc);

        processRunner.startProcess((File) storage.get(EXECUTABLE_DIR_STORAGE_KEY));

        File output = new File(runDir, "output.txt");
        checkOutput(output, answer);
    }

    private void checkOutput(File output, File answer) throws IOException {
        if (!output.exists()) {
            ++failed;
            return;
        }

        if (FileUtils.contentEqualsIgnoreEOL(output, answer, null)) {
            ++passed;
        } else {
            ++failed;
        }
    }
}
