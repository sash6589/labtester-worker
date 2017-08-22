package ru.ifmo.fitp.labtesterworker.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ifmo.fitp.labtesterworker.domain.report.SubmitReport;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;

import java.util.Date;

@Service
public class CheckService {

    private static final Logger LOG = Logger.getLogger(CheckService.class);


    public SubmitReport submit(TaskPipeline taskPipeline) {

        Date date = new Date();

        runTaskPipe(taskPipeline);
        SubmitReport report = collectOutput(taskPipeline);
        report.setDate(date);

        return report;
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
