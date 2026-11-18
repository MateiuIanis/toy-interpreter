
package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {

    @Test
    void putGetAndIsDefinedWork() throws ADTException {
        MyDictionary<String, Integer> dictionary = new MyDictionary<>();

        dictionary.put("x", 10);

        assertTrue(dictionary.isDefined("x"));
        assertEquals(10, dictionary.lookup("x"));

        dictionary.put("x", 20);
        assertEquals(20, dictionary.lookup("x"));
    }

    @Test
    void lookupOfMissingKeyThrowsException() {
        MyDictionary<String, Integer> dictionary = new MyDictionary<>();

        assertThrows(ADTException.class,
                () -> dictionary.lookup("missing"));

        assertFalse(dictionary.isDefined("missing"));
    }
}
