package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {
    @Test
    void firstStatementExecutesBeforeSecond() throws ADTException {
        IStmt first = new VarDeclStmt("x", new IntType());
        IStmt second = new VarDeclStmt("y", new IntType());

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), first);

        new CompStmt(first, second).execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
    }
}