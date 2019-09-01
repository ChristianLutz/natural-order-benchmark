package naturalsort.devexed;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>Comparator for ordering strings in <a href="https://en.wikipedia.org/wiki/Natural_sort_order">natural order</a>.
 * Compares strings case-insensitively and compares any numbers found in the string according to their magnitude, rather
 * than digit by digit. Additionally merges whitespace in text and ignores any whitespace around numbers for even more
 * human friendliness.</p>
 */
public final class NaturalOrderComparator<T extends CharSequence> implements Comparator<T> {

    private static Collator createDefaultCollator(Locale locale) {
        // Secondary strength collator which typically compares case-insensitively.
        Collator textCollator = Collator.getInstance(locale);
        textCollator.setStrength(Collator.SECONDARY);
        textCollator.setDecomposition(Collator.NO_DECOMPOSITION);
        return textCollator;
    }

    private static String trimText(CharSequence chars) {
        // Collapse whitespace.
        String text = whitespacePattern.matcher(chars).replaceAll(whitespaceReplacementString);

        // Trim start whitespace.
        if (!text.isEmpty() && text.charAt(0) == whitespaceReplacement) text = text.substring(1);

        // Trim end whitespace.
        if (!text.isEmpty() && text.charAt(text.length() - 1) == whitespaceReplacement)
            text = text.substring(0, text.length() - 1);

        return text;
    }

    // Unicode whitespace and digit matching.
    private static final String digitPatternString = "\\p{Nd}";
    private static final String whitespacePatternString = "[\\u0009-\\u000D\\u0020\\u0085\\u00A0\\u1680\\u180E\\u2000-\\u200A\\u2028\\u2029\\u202F\\u205F\\u3000]+";
    private static final Pattern whitespacePattern = Pattern.compile(whitespacePatternString);
    private static final char whitespaceReplacement = ' ';
    private static final String whitespaceReplacementString = "" + whitespaceReplacement;

    private final Collator textCollator;
    private final Pattern decimalChunkPatten;
    private final DecimalFormat decimalFormat;

    public NaturalOrderComparator() {
        this(Locale.getDefault());
    }

    public NaturalOrderComparator(Locale locale) {
        this(createDefaultCollator(locale), new DecimalFormatSymbols(locale));
    }

    public NaturalOrderComparator(Collator textCollator, DecimalFormatSymbols symbols) {
        this.textCollator = textCollator;

        // Relevant number symbols coerced to non-null strings.
        String minusSign = "" + symbols.getMinusSign();
        String groupingSeparator = "" + symbols.getGroupingSeparator();
        String decimalSeparator = "" + symbols.getDecimalSeparator();

        // Pattern to match numbers in the string.
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("(.*?)").append(whitespaceReplacement).append("*(");

        // Include negative numbers...
        if (!minusSign.isEmpty()) patternBuilder.append(Pattern.quote(minusSign)).append("?");

        // Include grouped number part (e.g. 1,000,000,000)...
        if (!groupingSeparator.isEmpty())
            patternBuilder.append("(").append(digitPatternString).append("+")
                    .append(Pattern.quote(groupingSeparator)).append(")*");

        // Include integer number part...
        patternBuilder.append(digitPatternString).append("+");

        // Include decimal part...
        if (!decimalSeparator.isEmpty())
            patternBuilder.append("(").append(Pattern.quote(decimalSeparator)).append(digitPatternString).append("+)?");

        patternBuilder.append(")");
        decimalChunkPatten = Pattern.compile(patternBuilder.toString());

        // Format to parse the numbers with three digits per group and a decimal part.
        decimalFormat = new DecimalFormat("0.#", symbols);
        decimalFormat.setParseBigDecimal(true);
    }

    private int compareText(CharSequence lhs, CharSequence rhs) {
        return textCollator.compare(trimText(lhs), trimText(rhs));
    }

    public String normalize(T text) {
        StringBuilder normalizedText = new StringBuilder();
        Matcher matcher = decimalChunkPatten.matcher(text);
        int end = 0;

        while (matcher.find()) {
            String textPart = matcher.group(1);
            String numberPart = matcher.group(2);
            String numberNormalized;

            try {
                numberNormalized = decimalFormat.parse(numberPart).toString();
            } catch (NumberFormatException | ParseException ex) {
                // Should be ignorable barring some mismatch between DecimalFormat and the number regex.
                // In which case we use the text value instead of the number value.
                numberNormalized = numberPart;
            }

            normalizedText.append(textPart);
            normalizedText.append(numberNormalized);
            end = matcher.end();
        }

        return normalizedText.append(text.subSequence(end, text.length())).toString();
    }

    public int normalizedKey(T text) {
        return Arrays.hashCode(textCollator.getCollationKey(normalize(text)).toByteArray());
    }

    @Override
    public int compare(T lhs, T rhs) {
        // Match strings against number pattern and compare the segments in each string one by one.
        Matcher lhsMatcher = decimalChunkPatten.matcher(lhs);
        Matcher rhsMatcher = decimalChunkPatten.matcher(rhs);

        // Start indexes of final non-number segment. For comparing the last text part of the strings.
        int lhsEnd = 0;
        int rhsEnd = 0;

        while (lhsMatcher.find() && rhsMatcher.find()) {
            String lhsTextPart = lhsMatcher.group(1);
            String rhsTextPart = rhsMatcher.group(1);
            String lhsNumberPart = lhsMatcher.group(2);
            String rhsNumberPart = rhsMatcher.group(2);

            // Compare prefix text part of match.
            int textCompare = compareText(lhsTextPart, rhsTextPart);
            if (textCompare != 0) return textCompare;

            try {
                // Parse numbers as big decimal and compare.
                BigDecimal lhsDecimal = (BigDecimal) decimalFormat.parse(lhsNumberPart);
                BigDecimal rhsDecimal = (BigDecimal) decimalFormat.parse(rhsNumberPart);

                int numberCompare = lhsDecimal.compareTo(rhsDecimal);
                if (numberCompare != 0) return numberCompare;
            } catch (NumberFormatException | ParseException ex) {
                // Should be ignorable barring some mismatch between DecimalFormat and the number regex.
                // In which case we compare the numbers as text.
                int numberCompare = compareText(lhsNumberPart, rhsNumberPart);
                if (numberCompare != 0) return numberCompare;
            }

            lhsEnd = lhsMatcher.end();
            rhsEnd = rhsMatcher.end();
        }

        // Compare final segment where one or both of lhs or rhs have no number part.
        return compareText(lhs.subSequence(lhsEnd, lhs.length()), rhs.subSequence(rhsEnd, rhs.length()));
    }

}