package ru.ifmo.fitp.labtesterworker.domain.transformer;

import org.springframework.stereotype.Component;
import ru.ifmo.fitp.labtesterworker.dao.task.*;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;
import ru.ifmo.fitp.labtesterworker.domain.task.codestyle.CheckCodestyle;
import ru.ifmo.fitp.labtesterworker.domain.task.git.GitClone;
import ru.ifmo.fitp.labtesterworker.domain.task.os.CleanEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.os.MoveTo;
import ru.ifmo.fitp.labtesterworker.domain.task.os.PrepareEnvironment;
import ru.ifmo.fitp.labtesterworker.domain.task.test.RunTests;


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
        if (taskDAO instanceof GitCloneDAO) {
            taskPipeline.addTask(new GitClone((GitCloneDAO) taskDAO));
        }
        if (taskDAO instanceof MoveToDAO) {
            taskPipeline.addTask(new MoveTo());
        }
        if (taskDAO instanceof PrepareEnvironmentDAO) {
            taskPipeline.addTask(new PrepareEnvironment());
        }
        if (taskDAO instanceof RunTestsDAO) {
            taskPipeline.addTask(new RunTests((RunTestsDAO) taskDAO));
        }
    }
}
