package ru.ifmo.fitp.labtesterworker.dao.task;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.ifmo.fitp.labtesterworker.dao.task.codestyle.CheckCodestyleDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneFileTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneSolutionDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.git.GitCloneTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.language.cpp.CompileCppDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.os.*;
import ru.ifmo.fitp.labtesterworker.dao.task.test.RunFileTestsDAO;
import ru.ifmo.fitp.labtesterworker.dao.task.test.RunTestsDAO;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GitCloneDAO.class, name = "gitClone"),
        @JsonSubTypes.Type(value = GitCloneSolutionDAO.class, name = "gitCloneSolution"),
        @JsonSubTypes.Type(value = GitCloneTestsDAO.class, name = "gitCloneTests"),
        @JsonSubTypes.Type(value = GitCloneFileTestsDAO.class, name = "gitCloneFileTests"),
        @JsonSubTypes.Type(value = CleanEnvironmentDAO.class, name = "cleanEnvironment"),
        @JsonSubTypes.Type(value = SolutionMoveToDAO.class, name = "solutionMoveTo"),
        @JsonSubTypes.Type(value = TestsMoveToDAO.class, name = "testsMoveTo"),
        @JsonSubTypes.Type(value = FileTestsMoveToDAO.class, name = "fileTestsMoveTo"),
        @JsonSubTypes.Type(value = PrepareEnvironmentDAO.class, name = "prepareEnvironment"),
        @JsonSubTypes.Type(value = RunTestsDAO.class, name = "runTests"),
        @JsonSubTypes.Type(value = RunFileTestsDAO.class, name = "runFileTests"),
        @JsonSubTypes.Type(value = CheckCodestyleDAO.class, name = "checkCodestyle"),
        @JsonSubTypes.Type(value = SaveSolutionDAO.class, name = "saveSolution"),
        @JsonSubTypes.Type(value = CompileCppDAO.class, name = "compileCppDAO")
})
public abstract class AbstractTaskDAO {

}
