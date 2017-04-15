package ru.ifmo.fitp.labtesterworker.service.submit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ifmo.fitp.labtesterworker.dao.task.AbstractTaskDAO;
import ru.ifmo.fitp.labtesterworker.dao.transformer.TaskTransformer;
import ru.ifmo.fitp.labtesterworker.dao.report.SubmitReport;
import ru.ifmo.fitp.labtesterworker.task.TaskPipeline;

@Service
public class SubmitService {

    private static final Logger LOG = Logger.getLogger(SubmitService.class);

    private TaskPipeline taskPipeline;

    public SubmitReport submit(AbstractTaskDAO[] tasksDAO) {

        taskPipeline = buildTaskPipe(tasksDAO);

        runTaskPipe();

        return collectOutput();
    }

    private TaskPipeline buildTaskPipe(AbstractTaskDAO[] tasksDAO) {
        LOG.info("Build task pipeline");

        return new TaskTransformer().transform(tasksDAO);
    }

    private void runTaskPipe() {
        LOG.info("Run task pipeline");

        taskPipeline.run();
    }


    private SubmitReport collectOutput() {
        LOG.info("Collect report");

        return taskPipeline.getReport();
    }
}
