package ru.digitalhabbits.homework2.main;

import ru.digitalhabbits.homework2.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileReaderInEachRow implements FileReader {
    @Override
    public Stream<String> readLines(File file) throws IOException {
        File filePath = new File(file.toURI());
        Path path = Path.of(filePath.getAbsolutePath());
        return Files.lines(path);
    }
}
