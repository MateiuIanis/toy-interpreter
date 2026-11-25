
package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    @Test
    void oneStepOnEmptyStackThrows() throws ADTException {
        PrgState state = PrgState.of(
                new VarDeclStmt("x", new IntType())
        );

        state.getExeStack().pop();

        Controller controller = new Controller(
                new Repository(state, dir.resolve("test-log.txt").toString())
        );

        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    @Test
    void logContainsAllSectionsInCorrectOrder() throws Exception {
        PrgState state = PrgState.of(
                new VarDeclStmt("x", new IntType())
        );

        Path log = dir.resolve("log.txt");
        Controller controller = new Controller(
                new Repository(state, log.toString())
        );

        controller.allStep();

        String content = Files.readString(log);

        int exeStack = content.indexOf("ExeStack:");
        int symTable = content.indexOf("SymTable:");
        int out = content.indexOf("Out:");
        int fileTable = content.indexOf("FileTable:");

        assertTrue(exeStack >= 0);
        assertTrue(symTable > exeStack);
        assertTrue(out > symTable);
        assertTrue(fileTable > out);
    }

    @Test
    void example5PrintsFive() throws Exception {
        PrgState state = PrgState.of(Examples.EX5);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex5-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                new IntValue(5),
                state.getOut().get(0)
        );
    }

    @Test
    void fileExampleReadsTwoNumbersAndClosesFile() throws Exception {
        Path input = dir.resolve("numbers.in");
        Files.writeString(input, "15\n50\n");

        PrgState state = PrgState.of(
                Examples.fileExample(input.toString())
        );

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex4-log.txt").toString())
        );

        controller.allStep();

        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(50), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleWithEmptyFilePrintsZeroTwice() throws Exception {
        Path input = dir.resolve("empty.in");
        Files.writeString(input, "");

        PrgState state = PrgState.of(
                Examples.fileExample(input.toString())
        );

        Controller controller = new Controller(
                new Repository(state, dir.resolve("empty-log.txt").toString())
        );

        controller.allStep();

        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void example6PrintsReferences() throws Exception {
        PrgState state = PrgState.of(Examples.EX6);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex6-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                List.of(
                        new RefValue(1, new IntType()),
                        new RefValue(2, new RefType(new IntType()))
                ),
                state.getOut().toList()
        );
    }

    @Test
    void example7ReadsNestedReferences() throws Exception {
        PrgState state = PrgState.of(Examples.EX7);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex7-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                List.of(new IntValue(20), new IntValue(25)),
                state.getOut().toList()
        );
    }

    @Test
    void example8WritesAndReadsHeap() throws Exception {
        PrgState state = PrgState.of(Examples.EX8);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex8-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                List.of(new IntValue(20), new IntValue(35)),
                state.getOut().toList()
        );
    }

    @Test
    void example9KeepsReachableHeapLocations() throws Exception {
        PrgState state = PrgState.of(Examples.EX9);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex9-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                List.of(new IntValue(20)),
                state.getOut().toList()
        );
    }

    @Test
    void example10ExecutesWhileLoop() throws Exception {
        PrgState state = PrgState.of(Examples.EX10);

        Controller controller = new Controller(
                new Repository(state, dir.resolve("ex10-log.txt").toString())
        );

        controller.allStep();

        assertEquals(
                List.of(
                        new IntValue(4),
                        new IntValue(3),
                        new IntValue(2),
                        new IntValue(1),
                        new IntValue(0)
                ),
                state.getOut().toList()
        );
    }
}
