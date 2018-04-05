package ru.ifmo.fitp.labtesterworker.domain.task;

import ru.ifmo.fitp.labtesterworker.domain.report.SubmitReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;

public class TaskPipeline {

    private Map<String, Object> storage;
    private List<Task> tasks;

    public TaskPipeline() {
        this.storage = new HashMap<>();
        this.tasks = new ArrayList<>();
    }

    public void setStorage(Map<String, Object> storage) {
        this.storage = storage;
    }

    public Map<String, Object> getStorage() {
        return storage;
    }

    public void addTask(AbstractTask task) {
        task.setStorage(storage);
        tasks.add(task);
    }

    public void run() {
        tasks.forEach(Task::perform);
    }

    public SubmitReport getReport() {
        SubmitReport report = new SubmitReport();

        StringBuilder result = new StringBuilder();

        String testStdout = (String) storage.getOrDefault(TESTS_RUN_STDOUT_STORAGE_KEY, "");
        String testStderr = (String) storage.getOrDefault(TESTS_RUN_STDERR_STORAGE_KEY, "");

        String codestyleStdout = (String) storage.getOrDefault(CODESTYLE_STDOUT_STORAGE_KEY, "");
        String codestyleStderr = (String) storage.getOrDefault(CODESTYLE_STDERR_STORAGE_KEY, "");

        String fileTestsResult = (String) storage.getOrDefault(FILE_TESTS_RESULT_STORAGE_KEY, "");

        if (!"".equals(testStdout)) {
            result.append("Tests result:\n").append(testStdout).append("\n").append("\n");
        }
        if (!"".equals(testStderr)) {
            result.append("Tests result:\n").append(testStderr).append("\n").append("\n");
        }
        if (!"".equals(codestyleStdout)) {
            result.append("Codestyle result:\n").append(codestyleStdout).append("\n").append("\n");
        }
        if (!"".equals(codestyleStderr)) {
            result.append(codestyleStderr).append("\n").append("\n");
        }
        if (!"".equals(fileTestsResult)) {
            result.append("Test result:\n").append(fileTestsResult).append("\n").append("\n");
        }

        report.setFileTestsResult(result.toString());

        return report;
    }
}
