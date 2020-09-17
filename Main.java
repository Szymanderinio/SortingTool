package sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private static boolean inputFile;
    private static boolean outputFile;
    private static File file;
    public static void main(final String[] args) {
        List<String> argsList = Arrays.asList(args);
        Set<String> correctArgs1 = Set.of("-sortingType", "natural", "byCount", "-dataType", "long", "line", "word", "-inputFile", "-outputFile");

        HashSet<String> argsSet = new HashSet<>(Arrays.asList(args));
        argsSet.removeAll(correctArgs1);
        argsSet.forEach(a -> System.out.println("\"" + a + "\" isn't a valid parameter. It's skipped."));


        String sortingType = "natural";
        if (argsList.contains("-sortingType")) {
            String secArg = null;
            try {
                secArg = argsList.get(argsList.indexOf("-sortingType") + 1);
            } catch (Exception e) {
                System.out.println("No sorting type defined!");
                System.exit(0);
            }
            if (secArg.equals("byCount")) sortingType = "byCount";
        }

        if (argsList.contains("-dataType")) {
            String dataTypeArgument = null;
            try {
                dataTypeArgument = argsList.get(argsList.indexOf("-dataType") + 1);
            } catch (Exception e) {
                System.out.println("No data type defined!");
                System.exit(0);
            }
            switch (dataTypeArgument) {
                case "long":
                    longSeparator(sortingType);
                    break;
                case "line":
                    lineSeparator(sortingType);
                    break;
                default:
                    wordSeparator(sortingType);
            }
        }


        inputFile = argsList.contains("-inputFile");
        outputFile = argsList.contains("-outputFile");

        if (inputFile || outputFile) {
            file = new File (argsList.get(argsList.size()-1));
        }

    }


    private static void wordSeparator(String sortingType) {
        if (sortingType.equals("natural")) wordSeparatorNatural();
        if (sortingType.equals("byCount")) wordSeparatorByCount();
    }

    private static void wordSeparatorNatural() {
        List<String> list = new ArrayList<>();
        int counter = 0;
        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] splitedLineIntoWords = line.split("\\s+");
                    for (String oneWord : splitedLineIntoWords) {
                        counter++;
                        list.add(oneWord);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitedLineIntoWords = line.split("(\\s+)");
                for (String oneWord : splitedLineIntoWords) {
                    counter++;
                    list.add(oneWord);
                }
            }
        }
        Collections.sort(list);
        if (outputFile) {
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.printf("Total lines: %d\n", counter);
            printWriter.println("Sorted data: ");
            for (String word : list) {
                printWriter.print(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        } else {
            System.out.printf("Total lines: %d\n", counter);
            System.out.println("Sorted data: ");
            for (String word : list) {
                System.out.print(word);
            }
        }
    }
    private static void wordSeparatorByCount() {
        HashMap<String, Integer> dataHashMap = new HashMap<>();
        int counter = 0;
        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] splitedLineIntoWords = line.split("\\s+");
                    for (String oneWord : splitedLineIntoWords) {
                        counter++;
                        dataHashMap.put(oneWord, dataHashMap.getOrDefault(oneWord, 0) + 1);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] splitetLineIntoWords = line.split("(\\s+)");
                for (String oneWord : splitetLineIntoWords) {
                    counter++;
                    dataHashMap.put(oneWord, dataHashMap.getOrDefault(oneWord, 0) + 1);
                }
            }
        }
        TreeSet<Integer> setOfValues = new TreeSet<>(dataHashMap.values());

        Map<Integer, Set<String>> reversedAndSetOfKeys = new HashMap<>();
        for (Integer integer : setOfValues) {
            Set<String> v = new TreeSet<>();
            for (String key : dataHashMap.keySet()) {
                if (Objects.equals(dataHashMap.get(key), integer)) v.add(key);
            }
            reversedAndSetOfKeys.put(integer, v);
        }

        if (outputFile) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.printf("Total words: %d\n", counter);
                for (Integer integer : setOfValues) {
                    for (String string : reversedAndSetOfKeys.get(integer)) {
                        printWriter.printf("%s: %d time(s), %d%%\n", string, integer, Math.round((100 * integer) / (float) counter));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.printf("Total words: %d.\n", counter);
            for (Integer integer : setOfValues) {
                for (String string : reversedAndSetOfKeys.get(integer)) {
                    System.out.printf("%s: %d time(s), %d%%\n", string, integer, Math.round((100 * integer) / (float) counter));
                }
            }
        }
    }


    private static void lineSeparator(String sortingType) {
        if (sortingType.equals("natural")) lineSeparatorNatural();
        if (sortingType.equals("byCount")) lineSeparatorByCount();
    }

    private static void lineSeparatorNatural() {
        List<String> list = new ArrayList<>();
        int counter = 0;
        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    counter++;
                    list.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                counter++;
                list.add(line);
            }
        }
        Collections.sort(list);
        if (outputFile) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.printf("Total lines: %d\n", counter);
                printWriter.println("Sorted data: ");
                for (String line : list) {
                    printWriter.println(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.printf("Total lines: %d\n", counter);
            System.out.println("Sorted data: ");
            for (String line : list) {
                System.out.println(line);
            }
        }
    }

    private static void lineSeparatorByCount() {
        HashMap<String, Integer> dataHashMap = new HashMap<>();
        int counter = 0;
        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    counter++;
                    dataHashMap.put(line, dataHashMap.getOrDefault(line, 0) +1);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                counter++;
                dataHashMap.put(line, dataHashMap.getOrDefault(line, 0) + 1);
            }
        }
        TreeSet<Integer> setOfValues = new TreeSet<>(dataHashMap.values());

        Map<Integer, Set<String>> reversedAndSetOfKeys = new HashMap<>();
        for (Integer integer : setOfValues) {
            Set<String> v = new TreeSet<>();
            for (String key : dataHashMap.keySet()) {
                if (Objects.equals(dataHashMap.get(key), integer)) v.add(key);
            }
            reversedAndSetOfKeys.put(integer, v);
        }

        if (outputFile) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.printf("Total numbers: %d.\n", counter);
                for (Integer integer : setOfValues) {
                    for (String string : reversedAndSetOfKeys.get(integer)) {
                        printWriter.printf("%s: %d time(s), %d%%\n", string, integer, Math.round((100 * integer) / (float) counter));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.printf("Total numbers: %d.\n", counter);
            for (Integer integer : setOfValues) {
                for (String string : reversedAndSetOfKeys.get(integer)) {
                    System.out.printf("%s: %d time(s), %d%%\n", string, integer, Math.round((100 * integer) / (float) counter));
                }
            }
        }
    }


    private static void longSeparator(String sortingType) {
        if (sortingType.equals("natural")) longSeparatorNatural();
        if (sortingType.equals("byCount")) longSeparatorByCount();
    }

    private static void longSeparatorNatural() {
        List<Long> longList = new ArrayList<>();

        int counter = 0;
        long l;

        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] split = scanner.nextLine().split("\\s+");
                    for (String s : split) {
                        try {
                            l = Long.parseLong(s);
                            counter++;
                            longList.add(l);
                        } catch (NumberFormatException e) {
                            System.out.println("\"" + s + "\" isn't a long. It's skipped.");
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            while (sc.hasNextLine()) {
                String[] split = sc.nextLine().split("\\s+");
                for (String s : split) {
                    try {
                        l = Long.parseLong(s);
                        counter++;
                        longList.add(l);
                    } catch (NumberFormatException e) {
                        System.out.println("\"" + s + "\" isn't a long. It's skipped.");
                    }
                }
            }
        }
        Collections.sort(longList);

        if (outputFile) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.printf("Total numbers: %d\n", counter);
                printWriter.println("Sorted data: ");
                for (Long aLong : longList) {
                    printWriter.print(aLong + " ");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.printf("Total numbers: %d\n", counter);
            System.out.print("Sorted data: ");
            for (Long aLong : longList) {
                System.out.print(aLong + " ");
            }
        }
    }

    private static void longSeparatorByCount() {
        TreeMap<Long, Integer> dataLong = new TreeMap<>();

        int counter = 0;
        long l;

        if (inputFile) {
            try (Scanner scanner = new Scanner(file)) {
                String[] split = scanner.nextLine().split("\\s+");
                for (String s : split) {
                    try {
                        l = Long.parseLong(s);
                        counter++;
                        dataLong.put(l, dataLong.getOrDefault(l, 0) + 1);
                    } catch (NumberFormatException e) {
                        System.out.println("\"" + s + "\" isn't a long. It's skipped.");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            while (sc.hasNextLine()) {
                String[] split = sc.nextLine().split("\\s+");
                for (String s : split) {
                    try {
                        l = Long.parseLong(s);
                        counter++;
                        dataLong.put(l, dataLong.getOrDefault(l, 0) + 1);
                    } catch (NumberFormatException e) {
                        System.out.println("\"" + s + "\" isn't a long. It's skipped.");
                    }
                }
            }
        }

        TreeSet<Integer> setOfValues = new TreeSet<>(dataLong.values());

        Map<Integer, Set<Long>> reversedAndSetOfKeys = new HashMap<>();
        for (Integer integer : setOfValues) {
            Set<Long> v = new LinkedHashSet<>();
            for (Long keyLong : dataLong.keySet()) {
                if (Objects.equals(dataLong.get(keyLong), integer)) v.add(keyLong);
            }
            reversedAndSetOfKeys.put(integer, v);
        }

        if (outputFile) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.printf("Total numbers: %d.\n", counter);
                for (Integer integer : setOfValues) {
                    for (Long aLong : reversedAndSetOfKeys.get(integer)) {
                        printWriter.printf("%s: %d time(s), %d%%\n", aLong, integer, Math.round((100 * integer) / (float) counter));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("Total numbers: %d.\n", counter);
            for (Integer integer : setOfValues) {
                for (Long aLong : reversedAndSetOfKeys.get(integer)) {
                    System.out.printf("%d: %d time(s), %d%%\n", aLong, integer, Math.round((100 * integer) / (float) counter));
                }
            }
        }
    }
}