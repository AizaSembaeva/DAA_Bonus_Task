package org.example.cli;

import org.example.algorithm.KMPMatcher;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CliRunner {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java KMPDemo <input-file> <output-file>");
            System.exit(1);
        }

        Path inputFile = Path.of(args[0]);
        Path outputFile = Path.of(args[1]);

        try {
            List<String> lines = Files.readAllLines(inputFile);

            if (lines.size() < 2) {
                System.err.println("Invalid sample file: must contain >= 2 lines.");
                return;
            }

            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < lines.size() - 1; i++) {
                textBuilder.append(lines.get(i));
                if (i < lines.size() - 2) textBuilder.append(" ");
            }
            String text = textBuilder.toString();

            String lastLine = lines.get(lines.size() - 1).trim();
            if (!lastLine.startsWith("PATTERN:")) {
                System.err.println("Invalid file: last line must be PATTERN: <pattern>");
                return;
            }

            String pattern = lastLine.substring("PATTERN:".length()).trim();

            KMPMatcher matcher = new KMPMatcher();
            List<Integer> matches = matcher.findAll(text, pattern);
            int first = matcher.findFirst(text, pattern);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.toFile()))) {
                writer.write("=== KMP File Test ===\n");
                writer.write("File: " + inputFile + "\n");
                writer.write("Pattern: \"" + pattern + "\"\n");
                writer.write("Text length: " + text.length() + "\n");

                if (matches.isEmpty()) {
                    writer.write("Matches: none\n");
                } else {
                    writer.write("Matches: " + matches + "\n");
                    writer.write("First index: " + first + "\n");
                }
            }

            System.out.println("Output written to " + outputFile);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
