package ru.digitalhabbits.homework2;

import static com.google.common.io.Resources.getResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import lombok.SneakyThrows;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Stream;

import ru.digitalhabbits.homework2.impl.AsyncFileLetterCounter;
import ru.digitalhabbits.homework2.main.FileReaderInEachRow;

public class E2ETests {

    @Test
    void async_file_letter_counting_should_return_predicted_count() {
        var file = getFile("test.txt");
        var counter = new AsyncFileLetterCounter();

        Map<Character, Long> count = counter.count(file);

        assertThat(count).containsOnly(
                entry('a', 2696L),
                entry('b', 2682L),
                entry('c', 2645L),
                entry('d', 2611L),
                entry('e', 2728L),
                entry('f', 2628L)
        );
    }

    @SneakyThrows
    @Test
    void readLines(){
        var file = getFile("test.txt");
        assertThat(file).isNotNull();
    }

    private File getFile(String name) {
        return new File(getResource(name).getPath());
    }
}
