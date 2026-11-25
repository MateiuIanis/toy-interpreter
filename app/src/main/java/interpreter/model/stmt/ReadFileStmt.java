package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(varName))
            throw new StatementException(
                    "readFile: variable " + varName + " is not declared"
            );

        if (!(state.getSymTable().lookup(varName) instanceof IntValue))
            throw new StatementException(
                    "readFile: variable " + varName + " is not an integer"
            );

        if (!(exp.eval(state.getSymTable(), state.getHeap()) instanceof StringValue fileName))
            throw new StatementException(
                    "readFile: " + exp + " is not a string"
            );

        var fileTable = state.getFileTable();

        if (!fileTable.isDefined(fileName))
            throw new StatementException(
                    "readFile: file " + fileName + " is not open"
            );

        try {
            BufferedReader reader = fileTable.lookup(fileName);
            String line = reader.readLine();

            int number = (line == null) ? 0 : Integer.parseInt(line.strip());

            state.getSymTable().update(varName, new IntValue(number));

        } catch (IOException e) {
            throw new StatementException(
                    "readFile: I/O error: " + e.getMessage()
            );
        } catch (NumberFormatException e) {
            throw new StatementException(
                    "readFile: file does not contain a valid integer"
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}