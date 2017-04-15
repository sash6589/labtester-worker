package ru.ifmo.fitp.labtesterworker.endpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.fitp.labtesterworker.dao.task.AbstractTaskDAO;
import ru.ifmo.fitp.labtesterworker.dao.report.SubmitReport;
import ru.ifmo.fitp.labtesterworker.service.submit.SubmitService;

@RestController
public class SubmitEndpoint {

    private static final Logger LOG = Logger.getLogger(SubmitEndpoint.class);

    private SubmitService submitService;

    @Autowired
    public SubmitEndpoint(SubmitService submitService) {

        this.submitService = submitService;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<SubmitReport> submit(@RequestBody AbstractTaskDAO[] tasksDAO) {

        LOG.info("New submit request");

        SubmitReport submitReport = submitService.submit(tasksDAO);

        return new ResponseEntity<>(submitReport, HttpStatus.OK);
    }
}
