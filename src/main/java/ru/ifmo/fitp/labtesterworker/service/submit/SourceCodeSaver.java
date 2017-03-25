package ru.ifmo.fitp.labtesterworker.service.submit;

import org.apache.log4j.Logger;
import ru.ifmo.fitp.labtesterworker.model.SourceCode;
import ru.ifmo.fitp.labtesterworker.util.Enviroment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SourceCodeSaver {

    private static final Logger LOG = Logger.getLogger(SourceCode.class);

    private SourceCode sourceCode;

    public SourceCodeSaver(SourceCode sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String save() {

        createDirectory();

        String programName = "Program" + sourceCode.getId() + ".py";
        String path = Enviroment.ENVIRONMENT_DIR_NAME + File.separator + programName;

        LOG.info("Saving source code to " + path);

        try (PrintWriter out = new PrintWriter(path)) {
            out.print(sourceCode.getSourceCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return programName;
    }

    private void createDirectory() {
        File dir = Enviroment.ENVIRONMENT_DIR;
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
}
