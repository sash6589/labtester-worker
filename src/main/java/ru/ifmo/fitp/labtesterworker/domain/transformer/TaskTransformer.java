package ru.ifmo.fitp.labtesterworker.domain.transformer;

import org.springframework.stereotype.Component;
import ru.ifmo.fitp.labtesterworker.dao.task.*;
import ru.ifmo.fitp.labtesterworker.dao.task.codestyle.CheckCodestyleDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneFileTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneSolutionDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.language.cpp.BuildCppDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.os.*;
import ru.ifmo.fitp.labtesterworker.dao.task.test.RunFileTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.test.RunTestsDAO;
import ru.ifmo.fitp.labtesterworker.domain.task.AbstractTask;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;
import ru.ifmo.fitp.labtesterworker.domain.task.codestyle.CheckCodestyle;
import ru.ifmo.fitp.labtesterworker.domain.task.git.GitClone;
import ru.ifmo.fitp.labtesterworker.domain.task.language.cpp.BuildCpp;
import ru.ifmo.fitp.labtesterworker.domain.task.os.CleanEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.os.MoveTo;
import ru.ifmo.fitp.labtesterworker.domain.task.os.PrepareEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.os.SaveSolution;
import ru.ifmo.fitp.labtesterworker.domain.task.test.RunFileTests;
import ru.ifmo.fitp.labtesterworker.domain.task.test.RunTests;

import static ru.ifmo.fitp.labtesterworker.domain.task.TaskUtils.*;


@Component
public class TaskTransformer {

    private TaskPipeline taskPipeline;

    public TaskPipeline transform(AbstractTaskDAO[] tasksDAO) {
        taskPipeline = new TaskPipeline();

        for (AbstractTaskDAO taskDAO: tasksDAO) {
            taskPipeline.addTask(parseTask(taskDAO));
        }

        return taskPipeline;
    }

    private AbstractTask parseTask(AbstractTaskDAO taskDAO) {
        if (taskDAO instanceof CheckCodestyleDAO) {
            return new CheckCodestyle((CheckCodestyleDAO) taskDAO);
        }
        if (taskDAO instanceof CleanEnvironmentDAO) {
            return new CleanEnvironment();
        }
        if (taskDAO instanceof GitCloneSolutionDAO) {
            return new GitClone((GitCloneSolutionDAO) taskDAO, SOLUTION_REPOSITORY_DIR_STORAGE_KEY,
                    SOLUTION_REPOSITORY_DIR_NAME_STORAGE_KEY);
        }
        if (taskDAO instanceof GitCloneTestsDAO) {
            return new GitClone((GitCloneTestsDAO) taskDAO, TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    TESTS_REPOSITORY_DIR_NAME_STORAGE_KEY);
        }
        if (taskDAO instanceof GitCloneFileTestsDAO) {
            return new GitClone((GitCloneFileTestsDAO) taskDAO, FILE_TESTS_REPOSITORY_DIR_STORAGE_KEY,
                    FILE_TESTS_REPOSITORY_DIR_NAME_STORAGE_KEY);
        }
        if (taskDAO instanceof SolutionMoveToDAO) {
            return new MoveTo(SOLUTION_REPOSITORY_DIR_STORAGE_KEY, EXECUTABLE_DIR_STORAGE_KEY,
                    EXECUTABLE_DIR_NAME_STORAGE_KEY, "run");
        }
        if (taskDAO instanceof TestsMoveToDAO) {
            return new MoveTo(TESTS_REPOSITORY_DIR_STORAGE_KEY, TESTS_DIR_STORAGE_KEY,
                    TESTS_DIR_NAME_STORAGE_KEY, "tests");
        }
        if (taskDAO instanceof FileTestsMoveToDAO) {
            return new MoveTo(FILE_TESTS_REPOSITORY_DIR_STORAGE_KEY, FILE_TESTS_DIR_STORAGE_KEY,
                    FILE_TESTS_DIR_NAME_STORAGE_KEY, "file-tests");
        }
        if (taskDAO instanceof PrepareEnvironmentDAO) {
            return new PrepareEnvironment();
        }
        if (taskDAO instanceof RunTestsDAO) {
            return new RunTests((RunTestsDAO) taskDAO);
        }
        if (taskDAO instanceof RunFileTestsDAO) {
            return new RunFileTests((RunFileTestsDAO) taskDAO);
        }
        if (taskDAO instanceof SaveSolutionDAO) {
            return new SaveSolution((SaveSolutionDAO) taskDAO);
        }
        if (taskDAO instanceof BuildCppDAO) {
            return new BuildCpp((BuildCppDAO) taskDAO);
        }
        throw new IllegalArgumentException("Unknown type of task");
    }
}
