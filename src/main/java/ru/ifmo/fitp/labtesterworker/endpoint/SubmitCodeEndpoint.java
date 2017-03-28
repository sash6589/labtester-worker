package ru.ifmo.fitp.labtesterworker.endpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.fitp.labtesterworker.model.ProgramOutput;
import ru.ifmo.fitp.labtesterworker.model.SourceCode;
import ru.ifmo.fitp.labtesterworker.service.submit.SubmitService;

@RestController
public class SubmitCodeEndpoint {

    private static final Logger LOG = Logger.getLogger(SubmitCodeEndpoint.class);

    private SubmitService submitService;

    @Autowired
    public SubmitCodeEndpoint(SubmitService submitService) {

        this.submitService = submitService;
    }

    @RequestMapping(value = "/submit-code", method = RequestMethod.POST)
    public ResponseEntity<ProgramOutput> sourceCode(@RequestBody SourceCode sourceCode) {

        LOG.info("New submit request with id " + sourceCode.getId());

        ProgramOutput programOutput = submitService.process(sourceCode);

        return new ResponseEntity<>(programOutput, HttpStatus.OK);
    }
}
