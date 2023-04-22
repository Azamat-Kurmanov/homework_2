package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.main.LetterCounterInEachRow;
import ru.digitalhabbits.homework2.main.LetterCounterMergerSum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

//todo Make your impl
public class AsyncFileLetterCounter implements FileLetterCounter {

    private final ArrayBlockingQueue<Future<Map<Character, Long>>> queueList;

    public AsyncFileLetterCounter() {
        this.queueList = new ArrayBlockingQueue<>(100);
    }

    @Override
    public Map<Character, Long> count(File input) {
        Path path = Path.of(input.getAbsolutePath());
        ExecutorService countEachRow = Executors.newSingleThreadExecutor();    //--count number of letters in each row
        ExecutorService sumTotalRows = Executors.newSingleThreadExecutor();    //--count the number of letters within the whole file

        LetterCountMerger countMerger = new LetterCounterMergerSum();   //--Initializing with ConcurrentHashMap
        try (Stream<String> reader = Files.lines(path)) {
                reader.forEach(it -> {
                        getNumberOfLetters(it, countEachRow);
                        sumTotalRows.submit(() -> {
                            try {
                                countMerger.merge(queueList.take().get());
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                );
        } catch (IOException e) {
            System.out.println("error: " + e);
        }

        countEachRow.shutdown();
        sumTotalRows.shutdown();

        return countMerger.totalCount();
    }

    /**
     *Counting each letter in each line and then outputting the results to each map entry
     */
    private void getNumberOfLetters(String text, ExecutorService countEachRow){
        LetterCounterInEachRow counter = new LetterCounterInEachRow();
        try {
            queueList.put(countEachRow.submit(() -> counter.count(text)));
        } catch (InterruptedException e) {
            countEachRow.shutdown();
            throw new RuntimeException(e);
        }
    }
}
