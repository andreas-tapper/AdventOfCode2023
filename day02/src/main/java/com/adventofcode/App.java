package com.adventofcode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class App
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Day 2 of Advent of Code 2023." );

        if(args.length < 1) {
            System.out.println("usage <filename with inputs>");
            return;
        }

        if("1".equals(args[0])) {
            partOne(args[1]);
        } else if("2".equals(args[0])) {
            partTwo(args[1]);
        }
    }

    private static void partOne(String input) throws IOException {
        try(var reader = new BufferedReader(new FileReader(input, Charset.forName("UTF-8")))) {
            var lines = reader.lines().toList();
            var drawPattern = Pattern.compile("(?<amount>\\d+)\\s+(?<color>\\w+)", Pattern.CASE_INSENSITIVE);
            var gems = new HashMap<String, Integer>();
            gems.put("red", 12);
            gems.put("green", 13);
            gems.put("blue", 14);

            var sum = 0;
            for (var line : lines) {
                var game = Integer.parseInt(line.substring(5, line.indexOf(':')));
                var valid = Arrays.stream(line.substring(line.indexOf(':') + 1).split(";"))
                        .map(x -> x.split(","))
                        .flatMap(Arrays::stream)
                        .map(String::trim)
                        .map(drawPattern::matcher)
                        .filter(Matcher::matches)
                        .map(x -> new Draw(x.group("color"), Integer.parseInt(x.group("amount"))))
                        .allMatch(x -> gems.get(x.gem) >= x.count);

                if (valid) {
                    sum += game;
                }
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }

    private static void partTwo(String input) throws IOException {
        try(var reader = new BufferedReader(new FileReader(input, Charset.forName("UTF-8")))) {
            var lines = reader.lines().toList();
            var drawPattern = Pattern.compile("(?<amount>\\d+)\\s+(?<color>\\w+)", Pattern.CASE_INSENSITIVE);
            var gems = new HashMap<String, Integer>();
            gems.put("red", 12);
            gems.put("green", 13);
            gems.put("blue", 14);

            var sum = 0;
            for (var line : lines) {
                var game = Integer.parseInt(line.substring(5, line.indexOf(':')));
                var draw = Arrays.stream(line.substring(line.indexOf(':') + 1).split(";"))
                        .map(x -> x.split(","))
                        .flatMap(Arrays::stream)
                        .map(String::trim)
                        .map(drawPattern::matcher)
                        .filter(Matcher::matches)
                        .map(x -> new Draw(x.group("color"), Integer.parseInt(x.group("amount"))))
                        .toList();

                var red = draw.stream()
                        .filter(x -> x.gem.equals("red"))
                        .max(Comparator.comparingInt(a -> a.count))
                        .orElseThrow();
                var green = draw.stream()
                        .filter(x -> x.gem.equals("green"))
                        .max(Comparator.comparingInt(a -> a.count))
                        .orElseThrow();
                var blue = draw.stream()
                        .filter(x -> x.gem.equals("blue"))
                        .max(Comparator.comparingInt(a -> a.count))
                        .orElseThrow();

                sum += red.count * green.count * blue.count;
            }

            System.out.printf("Answer is: %d%n", sum);

        }
    }

    private record Draw(String gem, int count) {}
}