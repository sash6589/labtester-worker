package ru.ifmo.fitp.labtesterworker.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.fitp.labtesterworker.dao.task.TasksDAO;
import ru.ifmo.fitp.labtesterworker.domain.report.SubmitReport;
import ru.ifmo.fitp.labtesterworker.domain.task.TaskPipeline;
import ru.ifmo.fitp.labtesterworker.domain.transformer.TaskTransformer;
import ru.ifmo.fitp.labtesterworker.service.SubmitService;

@RestController
public class SubmitEndpoint {

    private static final Logger LOG = Logger.getLogger(SubmitEndpoint.class);

    private final SubmitService submitService;
    private final TaskTransformer taskTransformer;

    @Autowired
    public SubmitEndpoint(SubmitService submitService, TaskTransformer taskTransformer) {
        this.submitService = submitService;
        this.taskTransformer = taskTransformer;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<SubmitReport> submit(@RequestBody TasksDAO tasksDAO) {

        LOG.info("New submit request");

        TaskPipeline taskPipeline = taskTransformer.transform(tasksDAO.getTasks());
        SubmitReport submitReport = submitService.submit(taskPipeline);

        return new ResponseEntity<>(submitReport, HttpStatus.OK);
    }
}
