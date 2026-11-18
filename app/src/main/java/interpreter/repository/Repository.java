package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.exceptions.RepositoryException;
import interpreter.exceptions.MyException;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private final List<PrgState> prgList;
    private final String logFilePath;

    public Repository(PrgState initial, String logFilePath) {
        this.prgList = new ArrayList<>();
        prgList.add(initial);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return prgList.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) {

            logFile.println(getCrtPrg().toLogString());

        } catch (IOException e) {
            throw new RepositoryException(
                    "cannot write the log file " + logFilePath + ": " + e.getMessage()
            );
        }
    }
}