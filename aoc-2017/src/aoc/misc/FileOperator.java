package aoc.misc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public interface FileOperator {

    public default String readFileToString(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    public default String readDay(int day) throws IOException {
        return readFileToString(new File("input/input_" + day));
    }
}
