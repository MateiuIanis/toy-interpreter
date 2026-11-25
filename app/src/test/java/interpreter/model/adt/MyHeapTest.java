package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private MyHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsIncreasingAddresses() {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    @Test
    void getReturnsValueAtAddress() throws ADTException {
        heap.allocate(new IntValue(10));

        assertEquals(new IntValue(10), heap.get(1));
    }

    @Test
    void updateChangesValueAtAddress() throws ADTException {
        heap.allocate(new IntValue(10));

        heap.update(1, new IntValue(50));

        assertEquals(new IntValue(50), heap.get(1));
    }

    @Test
    void getMissingAddressThrows() {
        assertThrows(
                ADTException.class,
                () -> heap.get(100)
        );
    }

    @Test
    void updateMissingAddressThrows() {
        assertThrows(
                ADTException.class,
                () -> heap.update(100, new IntValue(50))
        );
    }
}