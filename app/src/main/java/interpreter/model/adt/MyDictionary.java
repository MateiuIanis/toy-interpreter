package interpreter.model.adt;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> content;

    public MyDictionary() {
        content = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return content.containsKey(key);
    }

    @Override
    public V get(K key) {
        return content.get(key);
    }

    @Override
    public void put(K key, V value) {
        content.put(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}