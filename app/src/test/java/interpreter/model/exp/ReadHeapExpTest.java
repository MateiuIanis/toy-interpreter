
package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {
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
    void readHeapReturnsStoredValue() throws MyException {
        new interpreter.model.stmt.NewStmt(
                "v", new ValueExp(new IntValue(20))
        ).execute(state);

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertEquals(
                new IntValue(20),
                exp.eval(state.getSymTable(), state.getHeap())
        );
    }

    @Test
    void readHeapWithNonReferenceExpressionThrows() {
        ReadHeapExp exp = new ReadHeapExp(
                new ValueExp(new IntValue(10))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap())
        );
    }

    @Test
    void readHeapWithMissingAddressThrows() throws MyException {
        state.getSymTable().update(
                "v",
                new RefValue(100, new IntType())
        );

        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap())
        );
    }
}
