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

class IfStmtTest {
    private PrgState freshState() {
        return new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new VarDeclStmt("x", new IntType()));
    }

    @Test
    void trueConditionPushesThenBranch() throws MyException {
        PrgState state = freshState();
        IStmt thenBranch = new VarDeclStmt("y", new IntType());
        IStmt elseBranch = new VarDeclStmt("z", new IntType());

        new IfStmt(new ValueExp(new BoolValue(true)), thenBranch, elseBranch)
                .execute(state);

        assertSame(thenBranch, state.getExeStack().pop());
    }

    @Test
    void falseConditionPushesElseBranch() throws MyException {
        PrgState state = freshState();
        IStmt thenBranch = new VarDeclStmt("y", new IntType());
        IStmt elseBranch = new VarDeclStmt("z", new IntType());

        new IfStmt(new ValueExp(new BoolValue(false)), thenBranch, elseBranch).execute(state);

        assertSame(elseBranch, state.getExeStack().pop());
    }

    @Test
    void nonBooleanConditionThrows() {
        PrgState state = freshState();

        assertThrows(StatementException.class,
                () -> new IfStmt(new ValueExp(new IntValue(1)),
                        new VarDeclStmt("z", new IntType()), new VarDeclStmt("z", new IntType())).execute(state));
    }
}