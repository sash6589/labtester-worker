package ru.ifmo.fitp.labtesterworker.service.submit;

import ru.ifmo.fitp.labtesterworker.model.ProgramOutput;
import ru.ifmo.fitp.labtesterworker.model.SourceCode;

public class SubmitService {

    private SourceCode sourceCode;

    public SubmitService(SourceCode sourceCode) {
        this.sourceCode = sourceCode;
    }

    public ProgramOutput process() {

        String programName = saveToFile();
        runProgram(programName);

        return collectOutput();
    }

    private String saveToFile() {
        SourceCodeSaver fileSaver = new SourceCodeSaver(sourceCode);
        return fileSaver.save();
    }

    private void runProgram(String programName) {
        ProgramRunner programRunner = new ProgramRunner(programName);
        programRunner.runProgram();
    }

    private ProgramOutput collectOutput() {
        OutputCollector outputCollector = new OutputCollector();
        return outputCollector.collectOutput();
    }
}
