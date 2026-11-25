
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    private PrgState createState(int initialValue) throws MyException {
        PrgState state = PrgState.of(
                new VarDeclStmt("dummy", new IntType())
        );

        state.getExeStack().pop();

        new VarDeclStmt("v", new IntType()).execute(state);
        state.getSymTable().update("v", new IntValue(initialValue));

        return state;
    }

    private IStmt createLoop() {
        return new WhileStmt(
                new RelationalExp(
                        RelOp.GT,
                        new VarExp("v"),
                        new ValueExp(new IntValue(0))
                ),
                new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new AssignStmt(
                                "v",
                                new ArithExp(
                                        ArithOp.SUB,
                                        new VarExp("v"),
                                        new ValueExp(new IntValue(1))
                                )
                        )
                )
        );
    }

    @Test
    void whileWithZeroIterations() throws MyException {
        PrgState state = createState(0);

        createLoop().execute(state);

        assertTrue(state.getExeStack().isEmpty());
        assertTrue(state.getOut().toList().isEmpty());
    }

    @Test
    void whileWithOneIteration() throws MyException {
        PrgState state = createState(1);

        createLoop().execute(state);

        assertEquals(2, state.getExeStack().toList().size());
    }

    @Test
    void whileWithSeveralIterations() throws MyException {
        PrgState state = createState(3);

        state.getExeStack().push(createLoop());

        while (!state.getExeStack().isEmpty()) {
            IStmt current = state.getExeStack().pop();
            current.execute(state);
        }

        assertEquals(
                java.util.List.of(
                        new IntValue(3),
                        new IntValue(2),
                        new IntValue(1)
                ),
                state.getOut().toList()
        );

        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void whileWithNonBooleanConditionThrows() {
        PrgState state = PrgState.of(
                new VarDeclStmt("dummy", new IntType())
        );

        IStmt loop = new WhileStmt(
                new ValueExp(new IntValue(1)),
                new PrintStmt(new ValueExp(new IntValue(10)))
        );

        assertThrows(
                StatementException.class,
                () -> loop.execute(state)
        );
    }
}
