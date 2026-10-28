package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void oneStepOnEmptyStackThrows() throws ADTException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new VarDeclStmt("x", new IntType()));

        state.getExeStack().pop();

        Controller controller = new Controller(new Repository(state));

        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }
}