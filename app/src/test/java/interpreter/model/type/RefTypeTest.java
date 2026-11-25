package interpreter.model.type;

import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void referenceTypeGetsDefaultValue() {
        RefType type = new RefType(new IntType());

        assertEquals(
                new RefValue(0, new IntType()),
                type.defaultValue()
        );
    }

    @Test
    void nestedReferenceTypesAreEqual() {
        RefType first = new RefType(
                new RefType(new IntType())
        );

        RefType second = new RefType(
                new RefType(new IntType())
        );

        assertEquals(first, second);
    }

    @Test
    void nestedReferenceTypeDiffersFromSimpleReferenceType() {
        RefType nested = new RefType(
                new RefType(new IntType())
        );

        RefType simple = new RefType(new IntType());

        assertNotEquals(nested, simple);
    }
}