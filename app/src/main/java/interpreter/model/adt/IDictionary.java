package interpreter.model.adt;

import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K key);

    V get(K key);

    void put(K key, V value);

    Map<K, V> getContent();
}