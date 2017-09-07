package ru.ifmo.fitp.labtesterworker.dao.task;

public class SaveSolutionDAO extends AbstractTaskDAO {
    private String program;

    public SaveSolutionDAO(String program) {
        this.program = program;
    }

    public SaveSolutionDAO() {
    }

    public String getProgram() {
        return program;
    }
}
