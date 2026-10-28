package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    @Test
    void undefinedVariableThrows() {
        MyDictionary<String, IValue> symTable = new MyDictionary<>();
        VarExp expression = new VarExp("x");

        assertThrows(ExpressionException.class, () -> expression.eval(symTable));
    }
}