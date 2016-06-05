package naturalsort.dacus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NaturalSorter {
    private static Pattern numberPattern = Pattern.compile("[0-9]+");
    private static Pattern symbolPattern = Pattern.compile("[^\\w]+");
    private static Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]+|[^\\w]+");

    public static List<String> sort(List<String> stringList) {
        NaturalComparator comparator = new NaturalComparator();

        Collections.sort(stringList, comparator);

        return stringList;
    }

    public static class NaturalComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            Matcher firstMatcher = pattern.matcher(o1);
            Matcher secondMatcher = pattern.matcher(o2);

            while (secondMatcher.find() && firstMatcher.find()) {
                String firstType = findType(firstMatcher.group(0));
                String secondType = findType(secondMatcher.group(0));
                String firstString = firstMatcher.group(0);
                String secondString = secondMatcher.group(0);

                int result = firstType.compareTo(secondType);
                if (result != 0) {
                    return result;
                }

                switch (firstType) {
                    case "asymbol":
                    case "word":
                        int compareResult = firstString.toLowerCase().compareTo(secondString.toLowerCase());
                        if (compareResult == 0) {
                            break;
                        } else {
                            return compareResult;
                        }
                    case "number":
                        Integer firstNumber = Integer.valueOf(firstString);
                        Integer secondNumber = Integer.valueOf(secondString);
                        int numberResult = firstNumber.compareTo(secondNumber);
                        if (numberResult == 0) {
                            break;
                        } else {
                            return numberResult;
                        }
                }
            }

            if (firstMatcher.find()) {
                return 1;
            } else if (secondMatcher.find()) {
                return -1;
            } else {
                return 0;
            }
        }

        public static String findType(String string) {
            if (isNumber(string)) {
                return "number";
            } else if (isSymbol(string)) {
                return "asymbol";
            } else {
                return "word";
            }
        }

        public static boolean isNumber(String string) {
            Matcher numberMatcher = numberPattern.matcher(string);
            return numberMatcher.find();
        }

        public static boolean isSymbol(String string) {
            Matcher symbolMatcher = symbolPattern.matcher(string);
            return symbolMatcher.find();
        }
    }
}
