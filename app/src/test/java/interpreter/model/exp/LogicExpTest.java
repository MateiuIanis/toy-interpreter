package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void andWorks() throws MyException {
        assertEquals(new BoolValue(false),
                new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable));
    }

    @Test
    void orWorks() throws MyException {
        assertEquals(new BoolValue(true),
                new LogicExp(LogicOp.OR, bool(false), bool(true)).eval(symTable));
    }

    @Test
    void nonBooleanOperandThrows() {
        IExp expression = new LogicExp(
                LogicOp.AND, new ValueExp(new IntValue(1)), bool(true)
        );
        assertThrows(ExpressionException.class, () -> expression.eval(symTable));
    }
}