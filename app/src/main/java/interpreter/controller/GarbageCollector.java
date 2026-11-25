
package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Collectors;

public final class GarbageCollector {

    private GarbageCollector() {}

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(
            Set<Integer> symTableAddr,
            Map<Integer, IValue> heap) {

        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public static Set<Integer> reachableAddresses(
            Set<Integer> roots,
            Map<Integer, IValue> heap) {

        Set<Integer> reachable = new HashSet<>();
        Queue<Integer> toVisit = new ArrayDeque<>(roots);

        while (!toVisit.isEmpty()) {
            int address = toVisit.remove();

            if (!heap.containsKey(address))
                continue;

            if (!reachable.add(address))
                continue;

            IValue value = heap.get(address);

            if (value instanceof RefValue ref)
                toVisit.add(ref.address());
        }

        return reachable;
    }

    public static Map<Integer, IValue> safeGarbageCollector(
            Set<Integer> symTableAddr,
            Map<Integer, IValue> heap) {

        Set<Integer> reachable = reachableAddresses(symTableAddr, heap);

        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
