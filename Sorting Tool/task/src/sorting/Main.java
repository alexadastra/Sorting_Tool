package sorting;

import java.util.*;

abstract class SortingElements {
    int total;
    int maxAppearance;
    Scanner scanner;
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

    public SortingElements(String type) {
        total = 0;
        maxAppearance = 0;
        sortingType = type;
        scanner = new Scanner(System.in);
    }

    public static short percentage(int a, int b){
        return (short) (100.0 * (float) a / (float) b );
    }
}

class SortingLongs extends SortingElements {
    ArrayList<Long> inputs;

    public SortingLongs(String type) {
        super(type);
        inputs = new ArrayList<>();
    }

    void parseElements() {
        while (scanner.hasNext()) {
            inputs.add(scanner.nextLong());
        }
    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        System.out.println("Total numbers: " + inputs.size() + ".");
        System.out.print("Sorted data: ");
        for (Long i : inputs) {
            System.out.print(i + " ");
        }
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

    public SortingLines(String type) {
        super(type);
        inputs = new ArrayList<>();
    }

    void parseElements() {
        while (scanner.hasNext()) {
            inputs.add(scanner.nextLine());
            total++;
        }

    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        System.out.println("Total lines: " + inputs.size() + ".");
        System.out.println("Sorted data: ");
        for (String i : inputs) {
            System.out.println(i);
        }
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

    public SortingWords(String type) {
        super(type);
    }

    void parseElements() {
        while (scanner.hasNext()) {
            inputs.add(scanner.next());
            total++;
        }
    }

    void sortElementsNaturally() {
        Collections.sort(inputs);
        System.out.println("Total words: " + inputs.size() + ".");
        System.out.print("Sorted data: ");
        for (String i : inputs) {
            System.out.print(i + " ");
        }
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
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-sortingType")) {
                sortingTypeString = args[i+1];
            }
            if (args[i].equals("-dataType")) {
                dataTypeString = args[i+1];
            }
        }

        return new String[] {dataTypeString, sortingTypeString};
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