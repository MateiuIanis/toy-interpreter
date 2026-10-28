package interpreter.model.adt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void addGetAndSizeWork() {
        MyList<Integer> list = new MyList<>();
        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }
}