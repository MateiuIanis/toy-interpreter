package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState freshState() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new VarDeclStmt("y", new IntType()));
    }

    @Test
    void assignmentUpdatesVariable() throws MyException {
        PrgState state = freshState();
        new VarDeclStmt("x", new IntType()).execute(state);
        new AssignStmt("x", new ValueExp(new IntValue(10))).execute(state);

        assertEquals(new IntValue(10), state.getSymTable().get("x"));
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        PrgState state = freshState();
        assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValueExp(new IntValue(10))).execute(state));
    }

    @Test
    void typeMismatchThrows() throws MyException {
        PrgState state = freshState();
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValueExp(new BoolValue(true))).execute(state));
    }
}