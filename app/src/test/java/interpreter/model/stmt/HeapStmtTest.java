
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(
                new VarDeclStmt("dummy", new IntType())
        );

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);
    }

    @Test
    void newAllocatesAndUpdatesVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        assertEquals(
                new RefValue(1, new IntType()),
                state.getSymTable().lookup("v")
        );
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newWithUndeclaredVariableThrows() {
        assertThrows(
                StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(20)))
                        .execute(state)
        );
    }

    @Test
    void newWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(20)))
                        .execute(state)
        );
    }

    @Test
    void newWithWrongValueTypeThrows() {
        assertThrows(
                StatementException.class,
                () -> new NewStmt("v", new ValueExp(new BoolValue(true)))
                        .execute(state)
        );
    }

    @Test
    void writeHeapChangesValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        new WriteHeapStmt("v", new ValueExp(new IntValue(30)))
                .execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void writeHeapWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30)))
                        .execute(state)
        );
    }

    @Test
    void writeHeapWithWrongValueTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);

        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true)))
                        .execute(state)
        );
    }

    @Test
    void writeHeapWithUndeclaredVariableThrows() {
        assertThrows(
                StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30)))
                        .execute(state)
        );
    }
}
