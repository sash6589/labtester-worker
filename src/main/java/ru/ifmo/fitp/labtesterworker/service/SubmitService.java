package ru.ifmo.fitp.labtesterworker.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ifmo.fitp.labtesterworker.domain.report.SubmitReport;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;

@Service
public class SubmitService {

    private static final Logger LOG = Logger.getLogger(SubmitService.class);


    public SubmitReport submit(TaskPipeline taskPipeline) {

        runTaskPipe(taskPipeline);

        return collectOutput(taskPipeline);
    }

    private void runTaskPipe(TaskPipeline taskPipeline) {
        LOG.info("Run task pipeline");

        taskPipeline.run();
    }


    private SubmitReport collectOutput(TaskPipeline taskPipeline) {
        LOG.info("Collect report");

        return taskPipeline.getReport();
    }
}
