
package interpreter.model.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {

    @Test
    void putGetAndIsDefinedWork() {
        MyDictionary<String, Integer> dictionary = new MyDictionary<>();

        dictionary.put("x", 10);

        assertTrue(dictionary.isDefined("x"));
        assertEquals(10, dictionary.get("x"));

        dictionary.put("x", 20);
        assertEquals(20, dictionary.get("x"));
    }

    @Test
    void getOfMissingKeyReturnsNull() {
        MyDictionary<String, Integer> dictionary = new MyDictionary<>();

        assertNull(dictionary.get("missing"));
        assertFalse(dictionary.isDefined("missing"));
    }
}
