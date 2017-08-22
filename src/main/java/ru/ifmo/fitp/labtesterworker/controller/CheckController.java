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
import ru.ifmo.fitp.labtesterworker.service.CheckService;

import java.io.UncheckedIOException;

@RestController
public class CheckController {

    private static final Logger LOG = Logger.getLogger(CheckController.class);

    private final CheckService checkService;
    private final TaskTransformer taskTransformer;

    @Autowired
    public CheckController(CheckService checkService, TaskTransformer taskTransformer) {
        this.checkService = checkService;
        this.taskTransformer = taskTransformer;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseEntity<SubmitReport> check(@RequestBody TasksDAO tasksDAO) {

        LOG.info("New submit request");
        TaskPipeline taskPipeline = taskTransformer.transform(tasksDAO.getTasks());
        SubmitReport submitReport = checkService.submit(taskPipeline);

        return new ResponseEntity<>(submitReport, HttpStatus.OK);
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Failed to check solution")
    @ExceptionHandler(UncheckedIOException.class)
    public void badRequest() {

    }
}
