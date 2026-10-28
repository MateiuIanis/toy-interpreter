package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    @Test
    void printAddsEvaluatedValueToOutput() throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), new VarDeclStmt("x", new IntType()));

        new PrintStmt(new ValueExp(new IntValue(42))).execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(42), state.getOut().get(0));
    }
}