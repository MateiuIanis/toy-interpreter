package interpreter.model.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void equalStringValuesHaveSameHashCode() {
        StringValue first = new StringValue("hello");
        StringValue second = new StringValue("hello");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}