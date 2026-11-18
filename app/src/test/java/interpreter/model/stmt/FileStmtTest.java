
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.adt.MyDictionary;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n12\n");

        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new VarDeclStmt("d", new IntType()));

        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @Test
    void openFileAddsItToFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertTrue(state.getFileTable().isDefined(
                new StringValue(((StringValue) file.eval(state.getSymTable())).val())
        ));
    }

    @Test
    void readGivesNumbersInOrderThenZeroAtEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(12), state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void openingSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(file).execute(state));
    }

    @Test
    void openingMissingFileThrows() {
        ValueExp missingFile = new ValueExp(
                new StringValue(dir.resolve("missing.in").toString())
        );

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(missingFile).execute(state));
    }

    @Test
    void openingNonStringExpressionThrows() {
        ValueExp invalidFile = new ValueExp(new IntValue(10));

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(invalidFile).execute(state));
    }

    @Test
    void readingFromClosedFileThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void readingIntoUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "y").execute(state));
    }

    @Test
    void readingIntoNonIntegerVariableThrows() throws MyException {
        new VarDeclStmt("s", new interpreter.model.type.StringType())
                .execute(state);
        new OpenRFileStmt(file).execute(state);

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "s").execute(state));
    }

    @Test
    void readingNonIntegerLineThrows() throws MyException, IOException {
        Path invalid = dir.resolve("invalid.in");
        Files.writeString(invalid, "abc\n");

        ValueExp invalidFile = new ValueExp(
                new StringValue(invalid.toString())
        );

        new OpenRFileStmt(invalidFile).execute(state);

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(invalidFile, "x").execute(state));
    }

    @Test
    void closeFileRemovesItFromFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);

        new CloseRFileStmt(file).execute(state);

        StringValue fileName = (StringValue) file.eval(state.getSymTable());
        assertFalse(state.getFileTable().isDefined(fileName));
    }

    @Test
    void closingFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(file).execute(state));
    }
}
