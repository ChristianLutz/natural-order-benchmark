package naturalsort.devexed;

import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NaturalOrderComparatorTest {

    private static String formatDouble(Locale locale, double number) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(locale));
        format.applyPattern("#,#00.#####");
        format.setParseBigDecimal(true);

        return format.format(number);
    }

    private static double randomNumber(double from, double to) {
        return from + Math.random() * (to - from);
    }

    private static int sign(int i) {
        return i == 0 ? 0 : (i < 0) ? -1 : 1;
    }

    private static void compareNumbers(Locale locale, Collator collator, double a, double b) {
        String as = "ab   c " + formatDouble(locale, a) + ";;234";
        String bs = "ab  c " +  formatDouble(locale, b) + ";;234   ";

        NaturalOrderComparator<String> comp = new NaturalOrderComparator<String>(collator, new DecimalFormatSymbols(locale));
        int expectedResult = Double.compare(a, b);
        int compareResult = comp.compare(as, bs);
        assertThat(sign(compareResult), is(sign(expectedResult)));
    }

    @Test
    public void testSortNumbers() {
        // Test numbers with and without minus sign, groups, and decimals.
        for (Locale locale: Locale.getAvailableLocales()) {
            for (int collationStrength : new int[]{ Collator.PRIMARY, Collator.SECONDARY, Collator.TERTIARY }) {
                Collator collator = Collator.getInstance();
                collator.setStrength(collationStrength);

                double million = 1000000;
                double a = randomNumber(-million, million);
                double b = randomNumber(-million, million);

                compareNumbers(locale, collator, a, b);
            }
        }
    }

    @Test
    public void testComparesNormalizedKeys() {
        // Only test locales where changing case matches collation comparison.
        for (Locale locale: new Locale[] {
                Locale.ENGLISH,
                Locale.GERMAN,
                Locale.FRANCE,
                Locale.CHINESE,
                Locale.JAPAN
        }) {
            double million = 1000000;
            String n = formatDouble(locale, randomNumber(-million, million));
            NaturalOrderComparator<String> comp = new NaturalOrderComparator<String>(locale);

            byte[] keyA = comp.normalizeForLookup("abc ".toUpperCase(locale) + n + " hsd".toLowerCase(locale));
            byte[] keyB = comp.normalizeForLookup("abc ".toLowerCase(locale) + n + " hsd".toUpperCase(locale));

            assertThat(keyA, is(keyB));
        }
    }

}

