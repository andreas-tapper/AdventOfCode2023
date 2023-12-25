package com.adventofcode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class App
{

    public static void main(String[] args ) throws IOException {
        System.out.println( "Day 4 of Advent of Code 2023." );

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
            var lines = reader.lines()
                    .map(x -> x.substring(x.indexOf(':') + 1))
                    .toList();

            int sum = 0;

            for(var line : lines) {
                var prize = 0;
                var divider = line.indexOf('|');
                var winning = Arrays.stream(line.substring(0, divider).split("\\s"))
                        .map(String::trim)
                        .filter(x -> !x.isEmpty())
                        .map(Integer::parseInt)
                        .toList();
                var numbers = Arrays.stream(line.substring(divider + 1).split("\\s"))
                        .map(String::trim)
                        .filter(x -> !x.isEmpty())
                        .map(Integer::parseInt)
                        .toList();

                for (var number : numbers) {
                    if (winning.contains(number)) {
                        if(prize == 0) {
                            prize = 1;
                        } else {
                            prize += prize;
                        }
                    }
                }


                sum += prize;
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }

    private static void partTwo(String input) throws IOException {
        try(var reader = new BufferedReader(new FileReader(input, Charset.forName("UTF-8")))) {
            var lines = reader.lines()
                    .collect(toList());

            var sum = 0;
            var validNumbers = Set.of(
                    new Number("1", "1"), new Number("one", "1"),
                    new Number("2", "2"), new Number("two", "2"),
                    new Number("3", "3"), new Number("three", "3"),
                    new Number("4", "4"), new Number("four", "4"),
                    new Number("5", "5"), new Number("five", "5"),
                    new Number("6", "6"), new Number("six", "6"),
                    new Number("7", "7"), new Number("seven", "7"),
                    new Number("8", "8"), new Number("eight", "8"),
                    new Number("9", "9"), new Number("nine", "9"));

            for (var line : lines) {
                var first = validNumbers.stream()
                        .map(x -> new Position(x, line.indexOf(x.Name)))
                        .filter(x -> x.Position >= 0)
                        .min(Comparator.comparingInt(a -> a.Position))
                        .orElseThrow()
                        .Number
                        .Value();
                var last = validNumbers.stream()
                        .map(x -> new Position(x, line.lastIndexOf(x.Name)))
                        .filter(x -> x.Position >= 0)
                        .max(Comparator.comparingInt(a -> a.Position))
                        .orElseThrow()
                        .Number
                        .Value();

                sum += Integer.parseInt(first + last);
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }

    private record Number(String Name, String Value) {}
    private record Position(Number Number, int Position) {}
}