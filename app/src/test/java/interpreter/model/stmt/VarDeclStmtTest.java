package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState freshState() {
        PrgState state = PrgState.of(
                new VarDeclStmt("x", new IntType())
        );
        return state;
    }

    @Test
    void declarationCreatesVariableWithDefaultValue() throws MyException {
        PrgState state = freshState();
        new VarDeclStmt("x", new IntType()).execute(state);

        assertTrue(state.getSymTable().isDefined("x"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void redeclaredVariableThrows() throws MyException {
        PrgState state = freshState();
        VarDeclStmt declaration = new VarDeclStmt("x", new IntType());
        declaration.execute(state);

        assertThrows(StatementException.class, () -> declaration.execute(state));
    }
}