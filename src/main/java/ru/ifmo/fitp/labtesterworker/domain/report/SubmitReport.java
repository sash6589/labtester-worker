package ru.ifmo.fitp.labtesterworker.domain.report;

import java.util.Date;

@SuppressWarnings("unused")
public class SubmitReport {

    private Date date;

    private String testStdout;
    private String testStderr;

    private String codestyleStdout;
    private String codestyleStderr;

    private String fileTestsResult;

    public SubmitReport() {

    }

    public String getTestStdout() {
        return testStdout;
    }

    public String getTestStderr() {
        return testStderr;
    }

    public String getFileTestsResult() {
        return fileTestsResult;
    }

    public void setFileTestsResult(String fileTestsResult) {
        this.fileTestsResult = fileTestsResult;
    }

    public String getCodestyleStdout() {
        return codestyleStdout;
    }

    public String getCodestyleStderr() {
        return codestyleStderr;
    }

    public void setCodestyleStdout(String codestyleStdout) {
        this.codestyleStdout = codestyleStdout;
    }

    public void setCodestyleStderr(String codestyleStderr) {
        this.codestyleStderr = codestyleStderr;
    }

    public void setTestStdout(String testStdout) {
        this.testStdout = testStdout;
    }

    public void setTestStderr(String testStderr) {
        this.testStderr = testStderr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
