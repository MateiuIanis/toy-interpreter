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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void additionWorks() throws MyException {
        assertEquals(new IntValue(5),
                new ArithExp(ArithOp.ADD, num(2), num(3)).eval(symTable));
    }

    @Test
    void subtractionWorks() throws MyException {
        assertEquals(new IntValue(1),
                new ArithExp(ArithOp.SUB, num(3), num(2)).eval(symTable));
    }

    @Test
    void multiplicationWorks() throws MyException {
        assertEquals(new IntValue(6),
                new ArithExp(ArithOp.MUL, num(2), num(3)).eval(symTable));
    }

    @Test
    void divisionWorks() throws MyException {
        assertEquals(new IntValue(4),
                new ArithExp(ArithOp.DIV, num(8), num(2)).eval(symTable));
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTree() throws MyException {
        IExp expression = new ArithExp(
                ArithOp.ADD, num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5))
        );
        assertEquals(new IntValue(17), expression.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp expression = new ArithExp(ArithOp.DIV, num(1), num(0));
        assertThrows(ExpressionException.class, () -> expression.eval(symTable));
    }

    @Test
    void nonIntegerOperandThrows() {
        IExp expression = new ArithExp(
                ArithOp.ADD, new ValueExp(new BoolValue(true)), num(1)
        );
        assertThrows(ExpressionException.class, () -> expression.eval(symTable));
    }
}