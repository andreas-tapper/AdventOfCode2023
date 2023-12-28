package com.adventofcode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;
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
            var cards = new HashMap<Integer, Integer>();
            var max = 0;

            for(var line : reader.lines().toList()) {
                var colon = line.indexOf(':');
                var card = Integer.parseInt(line.substring(4, colon).trim());

                cards.put(card, cards.getOrDefault(card, 0) + 1);

                var pipe = line.indexOf('|');
                var winning = Arrays.stream(line.substring(colon + 1, pipe).split("\\s"))
                        .map(String::trim)
                        .filter(x -> !x.isEmpty())
                        .map(Integer::parseInt)
                        .toList();
                var numbers = Arrays.stream(line.substring(pipe + 1).split("\\s"))
                        .map(String::trim)
                        .filter(x -> !x.isEmpty())
                        .map(Integer::parseInt)
                        .toList();

                var matches = 0;
                for (var number : numbers) {
                    if (winning.contains(number)) {
                        matches += 1;
                    }
                }

                var copies = cards.get(card) - 1;
                for (var i = 0; i < matches; i += 1) {
                    int copy = cards.getOrDefault(card + 1 + i, 0) + 1;

                    if(copies > 0) {
                        copy += copies;
                    }

                    cards.put(card + 1 + i, copy);
                }

                max += 1;
            }

            var sum = 0;
            for(var i=1; i <= max; i += 1) {
                sum += cards.get(i);
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }
}