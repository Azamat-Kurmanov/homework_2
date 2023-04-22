package ru.digitalhabbits.homework2.main;

import ru.digitalhabbits.homework2.LetterCounter;

import java.util.HashMap;
import java.util.Map;

public class LetterCounterInEachRow implements LetterCounter {
    //--Create map with counter for each letter in each row: a=2, b=2, c=3
    @Override
    public Map<Character, Long> count(String input) {
        Map<Character, Long> map = new HashMap<>();
        input.chars().forEach(it -> map.compute((char) it, (k, v) -> (v == null) ? 1 : v+1));
        return map;
    }
}
