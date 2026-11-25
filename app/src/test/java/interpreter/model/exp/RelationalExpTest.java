
package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private BoolValue evaluate(RelOp op, int left, int right) throws Exception {
        RelationalExp exp = new RelationalExp(
                op,
                new ValueExp(new IntValue(left)),
                new ValueExp(new IntValue(right))
        );

        return (BoolValue) exp.eval(
                new MyDictionary<String, IValue>(),
                new MyHeap()
        );
    }

    @Test
    void lessThan() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.LT, 3, 5));
        assertEquals(new BoolValue(false), evaluate(RelOp.LT, 5, 3));
    }

    @Test
    void lessOrEqual() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.LE, 3, 3));
        assertEquals(new BoolValue(false), evaluate(RelOp.LE, 5, 3));
    }

    @Test
    void equal() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.EQ, 4, 4));
        assertEquals(new BoolValue(false), evaluate(RelOp.EQ, 4, 5));
    }

    @Test
    void notEqual() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.NE, 4, 5));
        assertEquals(new BoolValue(false), evaluate(RelOp.NE, 4, 4));
    }

    @Test
    void greaterThan() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.GT, 5, 3));
        assertEquals(new BoolValue(false), evaluate(RelOp.GT, 3, 5));
    }

    @Test
    void greaterOrEqual() throws Exception {
        assertEquals(new BoolValue(true), evaluate(RelOp.GE, 5, 5));
        assertEquals(new BoolValue(false), evaluate(RelOp.GE, 3, 5));
    }

    @Test
    void nonIntegerOperandThrows() {
        RelationalExp exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(5))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(
                        new MyDictionary<String, IValue>(),
                        new MyHeap()
                )
        );
    }
}
