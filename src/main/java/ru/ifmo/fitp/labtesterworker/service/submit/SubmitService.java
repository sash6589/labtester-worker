package ru.ifmo.fitp.labtesterworker.service.submit;

import org.springframework.stereotype.Service;
import ru.ifmo.fitp.labtesterworker.model.ProgramOutput;
import ru.ifmo.fitp.labtesterworker.model.SourceCode;

@Service
public class SubmitService {

    public ProgramOutput process(SourceCode sourceCode) {

        String programName = saveToFile(sourceCode);
        runProgram(programName);
        ProgramOutput outout = collectOutput();
        cleanEnviroment();

        return outout;
    }

    private String saveToFile(SourceCode sourceCode) {
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

    private void cleanEnviroment() {
        new EnvironmentCleaner().clean();
    }
}
