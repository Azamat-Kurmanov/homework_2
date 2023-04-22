package ru.digitalhabbits.homework2;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Sequential file reader
 */
public interface FileReader {

    Stream<String> readLines(File file) throws IOException;

}
