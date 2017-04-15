package ru.ifmo.fitp.labtesterworker.task;

import ru.ifmo.fitp.labtesterworker.dao.report.SubmitReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifmo.fitp.labtesterworker.task.TaskUtils.*;

public class TaskPipeline {

    private Map<String, Object> storage;
    private List<Task> tasks;

    public TaskPipeline() {
        this.storage = new HashMap<>();
        this.tasks = new ArrayList<>();
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
        report.setTestStdout((String) storage.get(TESTS_RUN_STDOUT_STORAGE_KEY));
        report.setTestStderr((String) storage.get(TESTS_RUN_STDERR_STORAGE_KEY));
        report.setCodestyleStdout((String) storage.get(CODESTYLE_STDOUT_STORAGE_KEY));
        report.setCodestyleStderr((String) storage.get(CODESTYLE_STDERR_STORAGE_KEY));

        return report;
    }
}
