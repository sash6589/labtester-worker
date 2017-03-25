package ru.ifmo.fitp.labtesterworker.model;

public class SourceCode {

    private long id;
    private String sourceCode;

    public SourceCode(long id, String sourceCode) {
        this.id = id;
        this.sourceCode = sourceCode;
    }

    public long getId() {
        return id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    SourceCode() {

    }
}
