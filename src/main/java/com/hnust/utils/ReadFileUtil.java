package com.hnust.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFileUtil {

    public static String read(String filePath) {
        StringBuilder content = new StringBuilder("");
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                // 将每一行的内容追加到 StringBuilder 中
                content.append(line).append(System.lineSeparator());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
