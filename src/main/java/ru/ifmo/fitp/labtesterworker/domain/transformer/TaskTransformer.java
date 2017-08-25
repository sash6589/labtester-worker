package ru.ifmo.fitp.labtesterworker.domain.transformer;

import org.springframework.stereotype.Component;
import ru.ifmo.fitp.labtesterworker.dao.task.*;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;
import ru.ifmo.fitp.labtesterworker.domain.task.codestyle.CheckCodestyle;
import ru.ifmo.fitp.labtesterworker.domain.task.git.GitClone;
import ru.ifmo.fitp.labtesterworker.domain.task.os.CleanEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.os.MoveTo;
import ru.ifmo.fitp.labtesterworker.domain.task.os.PrepareEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.test.RunFileTests;
import ru.ifmo.fitp.labtesterworker.domain.task.test.RunTests;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;


@Component
public class TaskTransformer {

    private TaskPipeline taskPipeline;

    public TaskPipeline transform(AbstractTaskDAO[] tasksDAO) {
        taskPipeline = new TaskPipeline();

        for (AbstractTaskDAO taskDAO: tasksDAO) {
            add(taskDAO);
        }

        return taskPipeline;
    }

    private void add(AbstractTaskDAO taskDAO) {
        if (taskDAO instanceof CheckCodestyleDAO) {
            taskPipeline.addTask(new CheckCodestyle((CheckCodestyleDAO) taskDAO));
        }
        if (taskDAO instanceof CleanEnvironmentDAO) {
            taskPipeline.addTask(new CleanEnvironment());
        }
        if (taskDAO instanceof GitCloneSolutionDAO) {
            taskPipeline.addTask(new GitClone((GitCloneSolutionDAO) taskDAO,
                    SOLUTION_REPOSITORY_DIR_STORAGE_KEY,
                    SOLUTION_REPOSITORY_DIR_NAME_STORAGE_KEY));
        }
        if (taskDAO instanceof GitCloneTestsDAO) {
            taskPipeline.addTask(new GitClone((GitCloneTestsDAO) taskDAO,
                    TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    TESTS_REPOSITORY_DIR_NAME_STORAGE_KEY));
        }
        if (taskDAO instanceof GitCloneFileTestsDAO) {
            taskPipeline.addTask(new GitClone((GitCloneFileTestsDAO) taskDAO,
                    FILE_TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    FILE_TESTS_REPOSITORY_DIR_NAME_STORAGE_KEY));
        }
        if (taskDAO instanceof SolutionMoveToDAO) {
            taskPipeline.addTask(new MoveTo(SOLUTION_REPOSITORY_DIR_STORAGE_KEY,
                    EXECUTABLE_DIR_STORAGE_KEY,
                    EXECUTABLE_DIR_NAME_STORAGE_KEY,
                    "run"));
        }
        if (taskDAO instanceof TestsMoveToDAO) {
            taskPipeline.addTask(new MoveTo(TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    TESTS_DIR_STORAGE_KEY,
                    TESTS_DIR_NAME_STORAGE_KEY,
                    "tests"));
        }
        if (taskDAO instanceof FileTestsMoveToDAO) {
            taskPipeline.addTask(new MoveTo(FILE_TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    FILE_TESTS_DIR_STORAGE_KEY,
                    FILE_TESTS_DIR_NAME_STORAGE_KEY,
                    "file-tests"));
        }
        if (taskDAO instanceof PrepareEnvironmentDAO) {
            taskPipeline.addTask(new PrepareEnvironment());
        }
        if (taskDAO instanceof RunTestsDAO) {
            taskPipeline.addTask(new RunTests((RunTestsDAO) taskDAO));
        }
        if (taskDAO instanceof RunFileTestsDAO) {
            taskPipeline.addTask(new RunFileTests((RunFileTestsDAO) taskDAO));
        }
    }
}
