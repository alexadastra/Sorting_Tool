package sorting;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

abstract class SortingElements {
    int total;
    int maxAppearance;
    Scanner scanner;
    FileWriter writer;
    ArrayList<String> output;
    String sortingType;

    abstract void parseElements();

    abstract void sortElementsNaturally();

    abstract void sortElementsByCount();

    void sortElements(){
        if (sortingType.equals("natural")){
            sortElementsNaturally();
        } else {
            sortElementsByCount();
        }
    }

    public SortingElements(String type, String in, String out) {
        total = 0;
        maxAppearance = 0;
        sortingType = type;
        if (!(in.isBlank())) {
            try {
                scanner = new Scanner(new File(in));
            } catch (FileNotFoundException err) {
                System.out.println("No such input file!");
            }
        } else {
            scanner = new Scanner(System.in);
        }

        if (!(out.isBlank())) {
            try {
                writer = new FileWriter(new File(out));
            } catch (IOException err) {
                System.out.println("No such output file!");
            }
        }
        output = new ArrayList<>();
    }

    public void writeln(ArrayList<String> text) {
        if (writer != null) {
            for (String i : text) {
                try{
                    writer.write(i + '\n');
                } catch (IOException err){
                    System.out.println("exception occurred!");
                }
            }
        } else {
            for (String i : text) {
                System.out.println(i);
            }
        }
    }

    public static int percentage(int a, int b){
        return (int) Math.round(100.0 * (float) a / (float) b );
    }
}

class SortingLongs extends SortingElements {
    ArrayList<Long> inputs;

    public SortingLongs(String type, String in, String out) {
        super(type, in, out);
        inputs = new ArrayList<>();
    }

    void parseElements() {
        while (scanner.hasNext()) {
            String str = scanner.next();
            try{
                inputs.add(Long.parseLong(str));
            } catch (RuntimeException err) {
                System.out.println("\"" + str + "\"" + " isn't a valid parameter. It's skipped.");
            }
        }
    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        System.out.println("Total numbers: " + inputs.size() + ".");
        output.add("Sorted data: ");
        String nums = "";
        for (Long i : inputs) {
            nums = nums.concat(i + " ");
        }
        output.add(nums);
        writeln(output);
    }

    void sortElementsByCount() {
        int maxAppearance = 0;
        int total = 0;
        long maxLong = Long.MIN_VALUE;
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            total++;
            if (number == maxLong) {
                maxAppearance++;
            } else if (number > maxLong) {
                maxLong = number;
                maxAppearance = 1;
            }
        }
        System.out.println("Total numbers: " + total + ".");
        System.out.println("The greatest number: " + maxLong + " (" + maxAppearance + " time(s), "
                + percentage(maxAppearance, total) + "%).");
    }
}

class SortingLines extends SortingElements {
    ArrayList<String> inputs;
    Map<String, Integer> dataEntryToCount;
    List<Integer> counts;
    Map<Integer, Set<String>> countToDataEntries;

    public static short compareStrings(String first, String second) {
        if (first.length() > second.length()){
            return 1;
        } else if (second.length() > first.length()){
            return -1;
        } else {
            for (int i = 0; i < first.length(); i++) {
                if ((int)first.charAt(i) > (int)second.charAt(i)) {
                    return 1;
                } else if ((int)first.charAt(i) < (int)second.charAt(i)) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public SortingLines(String type, String in, String out) {
        super(type, in, out);
        inputs = new ArrayList<>();
        if (sortingType.equals("byCount")){
            dataEntryToCount = new HashMap<>();
            counts = new ArrayList<>();
            countToDataEntries = new TreeMap<>();
        }
    }

    void parseElements() {
        while (scanner.hasNext()) {
            inputs.add(scanner.nextLine());
            total++;
        }

    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        output.add("Total lines: " + inputs.size() + ".");
        output.add("Sorted data: ");
        for (String i : inputs) {
            output.add(i);
        }
        writeln(output);
    }

    void sortElementsByCount() {
        /*
        String maxLine = "";
        if (compareStrings(maxLine, word) == 0) {
                maxAppearance++;
            } else if (compareStrings(word, maxLine) == 1) {
                maxLine = word;
                maxAppearance = 1;
            }
        }
        System.out.println("Total words: " + total + ".");
        System.out.println("The longest line: ");
        System.out.println(maxLine);
        System.out.println("(" + maxAppearance + " time(s), "
                + percentage(maxAppearance, total) + "%).");
         */
    }
}

class SortingWords extends SortingLines {

    public SortingWords(String type, String in, String out) {
        super(type, in, out);
    }

    void parseElements() {
        while (scanner.hasNext()) {
            inputs.add(scanner.next());
            total++;
        }
    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        output.add("Total words: " + inputs.size() + ".");
        String words = "";
        for (String i : inputs) {
            words = words.concat(i + " ");
        }
        output.add(words);
        writeln(output);
    }

    void sortElementsByCount() {
        /*
        String maxWord = "";
        while (scanner.hasNext()) {
            String word = scanner.next();
            total++;
            if (compareStrings(maxWord, word) == 0) {
                maxAppearance++;
            } else if (compareStrings(word, maxWord) == 1) {
                maxWord = word;
                maxAppearance = 1;
            }
        }
        System.out.println("Total words: " + total + ".");
        System.out.println("The longest word: " + maxWord + " (" + maxAppearance + " time(s), "
                + percentage(maxAppearance, total) + "%).");

         */
    }
}

public class Main{

    static String[] parceArguments(final String[] args) {
        String dataTypeString = "";
        String sortingTypeString = "natural";
        String inputFileString = "";
        String outputFileString = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-sortingType")) {
                try{
                    sortingTypeString = args[i+1];
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("No sorting type defined!");
                }
            }
            else if (args[i].equals("-dataType")) {
                try{
                    dataTypeString = args[i+1];
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("No data type defined!");
                }
            }
            else if (args[i].equals("-inputFile")) {
                try{
                    inputFileString = args[i+1];
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("No input file defined!");
                }
            }
            else if (args[i].equals("-outputFile")) {
                try{
                    outputFileString = args[i+1];
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("No output file defined!");
                }
            }
            else if (args[i].startsWith("-")){
                System.out.println("\"" + args[i] + "\" isn't a valid parameter. It's skipped.");
            }
        }

        return new String[] {dataTypeString, sortingTypeString, inputFileString, outputFileString};
    }

    public static void main(final String[] args) {
        String[] arguments = parceArguments(args);

        SortingElements sorter;
        if (arguments[0].equals("word")){
            sorter = new SortingWords(arguments[1]);
        } else if (arguments[0].equals("long")){
            sorter = new SortingLongs(arguments[1]);
        } else {
            sorter = new SortingLines(arguments[1]);
        }
        sorter.parseElements();
        sorter.sortElements();
    }
}

/*
public enum sortingType {
        NATURAL, BYCOUNT;
    }

    public enum dataType {
        LONG, WORD, LINE;
    }
 */

/*
            switch dataType {
                case "word":
                    data = dataType.WORD;
                    break;
                case "line":
                    data = dataType.LINE;
                    break;
                case "long":
                    data = dataType.LONG;
                    break;
                default:
                    break;
            }
            switch sortingType {
                case "natural":
                    sort = sortingType.NATURAL;
                    break;
                case "byCount":
                    sort = sortingType.BYCOUNT;
                    break;
                default:
                    break;
            }
            */