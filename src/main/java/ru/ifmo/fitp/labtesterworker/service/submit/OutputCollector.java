package ru.ifmo.fitp.labtesterworker.service.submit;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.model.ProgramOutput;
import ru.ifmo.fitp.labtesterworker.util.Enviroment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputCollector {

    private static final Logger LOG = Logger.getLogger(OutputCollector.class);

    private String pathToOutput;

    public OutputCollector() {
        this.pathToOutput = Enviroment.ENVIRONMENT_DIR_NAME + File.separator + "output.txt";
    }

    public ProgramOutput collectOutput() {

        LOG.info("Collecting output");

        String data = readFromFile();

        return new ProgramOutput(data);
    }

    private String readFromFile() {
        StringBuilder output = new StringBuilder();
        try {
            Files.lines(Paths.get(pathToOutput)).forEach(output::append);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}
