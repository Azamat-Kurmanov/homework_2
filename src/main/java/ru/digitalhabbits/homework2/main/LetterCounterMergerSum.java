package ru.digitalhabbits.homework2.main;

import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LetterCounterMergerSum implements LetterCountMerger {

    private final Map<Character, Long> finalFirst;

    public LetterCounterMergerSum() {
        this.finalFirst = new ConcurrentHashMap<>();
    }

    //--Summing the count of each letter
    @Override
    public void merge(Map<Character, Long> characterLongMap) {
        characterLongMap.forEach((key, value) -> finalFirst.compute(key, (k, v) -> v == null ? 1 : v + value));
    }

    public Map<Character, Long> totalCount() {
        return finalFirst;
    }
}
