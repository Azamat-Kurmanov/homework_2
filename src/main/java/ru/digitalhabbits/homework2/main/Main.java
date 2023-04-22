package ru.digitalhabbits.homework2.main;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.FileReader;
import ru.digitalhabbits.homework2.impl.AsyncFileLetterCounter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String filePath = "test.txt";
        File file = new File(filePath);

        printTxtFromFile(file);
        countLettersInEachLine(file.getAbsolutePath()); //--Метод для подсчёта символов в файле
    }

    /**
     * Чтение обрабатываемого файла выполнять построчно в основном потоке.
     */
    private static void printTxtFromFile(File file){
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.submit(() -> {
                FileReader reader = new FileReaderInEachRow();
                try (Stream<String> lines = reader.readLines(file)) {
                    lines.forEach(it -> System.out.println("lines: " + it));
                } catch (IOException e) {
                    System.out.println("e: " + e);
                }
//            }
//        );
//        executor.shutdown();
    }

    /**
     * Подсчет количества символов в строках выполнять в отдельных потоках.
     */
    private static void countLettersInEachLine(String filePath){
        FileLetterCounter counter = new AsyncFileLetterCounter();
        Map<Character, Long> count = counter.count(new File(filePath));
        System.out.println("count: " + count);
    }
}
