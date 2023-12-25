package com.adventofcode;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class App
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Day 3 of Advent of Code 2023." );

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
            var sum = 0;
            String previous = null;
            var current = reader.readLine();
            var next = reader.readLine();

            IntPredicate isNumeric = x -> x >= 48 && x <= 57;
            IntPredicate isDot = x -> x == 46;
            BiPredicate<String, Integer> hasSymbol = (value, index) -> {
                if (value == null) return false;

                return (index - 1 > 0 && !isDot.test(value.charAt(index - 1)) && !isNumeric.test(value.charAt(index - 1)))
                        || (!isDot.test(value.charAt(index)) && !isNumeric.test(value.charAt(index)))
                        || (index + 1 < value.length() && !isDot.test(value.charAt(index + 1)) && !isNumeric.test(value.charAt(index + 1)));
            };
            BiFunction<String, Integer, Integer> indexOfSymbol = (value, index) -> {
                for(var i=index; i < value.length(); i += 1) {
                    if (!isNumeric.test(value.charAt(i))) {
                       return i;
                    }
                }
                return -1;
            };
            BiFunction<String, Integer, Integer> lastIndexOfSymbol = (value, index) -> {
                for(var i=index; i >= 0; i -= 1) {
                    if (!isNumeric.test(value.charAt(i))) {
                        return i;
                    }
                }
                return -1;
            };

            while(current != null) {
                for (var i=0; i < current.length(); i += 1) {
                    if (!isNumeric.test(current.charAt(i))) {
                        continue;
                    }

                    if (!hasSymbol.test(previous, i) && !hasSymbol.test(current, i) && !hasSymbol.test(next, i)) {
                        continue;
                    }

                    var back = lastIndexOfSymbol.apply(current, i);
                    var forward = indexOfSymbol.apply(current, i);
                    var value = current.substring(back + 1, forward == -1 ? current.length() : forward);
                    sum += Integer.parseInt(value);

                    if(forward == -1) break;
                    i = forward;
                }

                previous = current;
                current = next;
                next = reader.readLine();
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }

    private static void partTwo(String input) throws IOException {
        try(var reader = new BufferedReader(new FileReader(input, Charset.forName("UTF-8")))) {
            var sum = 0;
            String previous = null;
            var current = reader.readLine();
            var next = reader.readLine();

            IntPredicate isNumeric = x -> x >= 48 && x <= 57;
            IntPredicate isCog = x -> x == 42;
            BiFunction<String, Integer, Integer> indexOfSymbol = (value, index) -> {
                for(var i=index; i < value.length(); i += 1) {
                    if (!isNumeric.test(value.charAt(i))) {
                        return i;
                    }
                }
                return -1;
            };
            BiFunction<String, Integer, Integer> lastIndexOfSymbol = (value, index) -> {
                for(var i=index; i >= 0; i -= 1) {
                    if (!isNumeric.test(value.charAt(i))) {
                        return i;
                    }
                }
                return -1;
            };
            BiFunction<String, Integer, Integer> parseInteger = (value, index) -> {
                var back = lastIndexOfSymbol.apply(value, index);
                var forward = indexOfSymbol.apply(value, index);

                if (forward == -1) {
                   forward = value.length();
                }

                if(back >= forward) {
                    throw new IllegalStateException(String.format("[%d..%d] does not make sense in %s", back, forward, value));
                }

                var str = value.substring(back + 1, forward);
                return Integer.parseInt(str);
            };

            while(current != null) {
                for (var i=0; i < current.length(); i += 1) {
                    if (!isCog.test(current.charAt(i))) {
                        continue;
                    }

                    var numbers = new ArrayList<Integer>();

                    if(previous != null) {
                        if (isNumeric.test(previous.charAt(i))) {
                            numbers.add(parseInteger.apply(previous, i));
                        } else {
                            if(i > 0 && isNumeric.test(previous.charAt(i - 1))) {
                                numbers.add(parseInteger.apply(previous, i - 1));
                            }

                            if(i + 1 < previous.length() && isNumeric.test(previous.charAt(i + 1))) {
                                numbers.add(parseInteger.apply(previous, i + 1));
                            }
                        }
                    }

                    if(i > 0 && isNumeric.test(current.charAt(i - 1))) {
                        numbers.add(parseInteger.apply(current, i - 1));
                    }

                    if(i + 1 < current.length() && isNumeric.test(current.charAt(i + 1))) {
                        numbers.add(parseInteger.apply(current, i + 1));
                    }

                    if(next != null) {
                        if (isNumeric.test(next.charAt(i))) {
                            numbers.add(parseInteger.apply(next, i));
                        } else {
                            if(i > 0 && isNumeric.test(next.charAt(i - 1))) {
                                numbers.add(parseInteger.apply(next, i - 1));
                            }

                            if(i + 1 < next.length() && isNumeric.test(next.charAt(i + 1))) {
                                numbers.add(parseInteger.apply(next, i + 1));
                            }
                        }
                    }

                    if (numbers.size() == 2) {
                        sum += numbers.get(0) * numbers.get(1);
                    }

                    var forward = indexOfSymbol.apply(current, i);
                    if(forward == -1) break;
                    i = forward;
                }

                previous = current;
                current = next;
                next = reader.readLine();
            }

            System.out.printf("Answer is: %d%n", sum);
        }
    }

    private record Number(String Name, String Value) {}
    private record Position(Number Number, int Position) {}
}