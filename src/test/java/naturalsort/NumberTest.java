package naturalsort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.padler.natorder.NaturalOrderComparator;

import naturalsort.dacus.NaturalSorter;
import naturalsort.friedrich.Strings;
import naturalsort.koelle.AlphanumComparator;
import net.greypanther.natsort.SimpleNaturalComparator;

public class NumberTest {

    public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new NaturalSorter.NaturalComparator(),                                   // dacus
            new AlphanumComparator(),                                                // kolle
            new naturalsort.devexed.NaturalOrderComparator<String>(Locale.ENGLISH),  // berry
            Strings.getNaturalComparator(),                                          // friedrich
            new NaturalOrderComparator(),                                            // padler improved pour
            Comparator.<String>naturalOrder(),                                       // java.util
            SimpleNaturalComparator.<String>getInstance()                            // greypanther
        );
    }

    @ParameterizedTest
    @DisplayName("Sort numbers natural")
    @MethodSource( "comparators" )
    public void orderSimpleIntegers(Comparator<String> ouT) {
        assertThat(ouT.compare("0", "0"), equalTo(0));
        assertThat(ouT.compare("50", "50"), equalTo(0));
        assertThat(ouT.compare("2", "10"), lessThan(0));
        assertThat(ouT.compare("10", "2"), greaterThan(0));
        assertThat(ouT.compare("1102", "11001"), lessThan(0));
        assertThat(ouT.compare("11001", "1102"), greaterThan(0));
    }

    @ParameterizedTest
    @DisplayName("Handle leading zeros")
    @MethodSource( "comparators" )
    public void compareIntegerSubstringsWithLeadingZeros(Comparator<String> ouT) {
        assertThat(ouT.compare("000", "000"), equalTo(0));
        assertThat(ouT.compare("001", "001"), equalTo(0));
        assertThat(ouT.compare("00", "1"), lessThan(0));
//        assertThat(ouT.compare("01", "1"), greaterThan(0));
        assertThat(ouT.compare("00", "0001"), lessThan(0));
        assertThat(ouT.compare("010", "01"), greaterThan(0));
        assertThat(ouT.compare("010", "001"), greaterThan(0));
    }

    @ParameterizedTest
    @DisplayName("Ignore floating point")
    @MethodSource( "comparators" )
    public void compareShouldNotConsiderFloatingPoint(Comparator<String> ouT) {
        // should not consider a decimal point surrounded by integers as a floating point number
        assertThat(ouT.compare("0.01", "0.001"), lessThan(0));
        assertThat(ouT.compare("0.001", "0.01"), greaterThan(0));
        assertThat(ouT.compare("1.01", "1.001"), lessThan(0));
        assertThat(ouT.compare("1.001", "1.01"), greaterThan(0));
    }

    @ParameterizedTest
    @DisplayName("Sort very large numbers")
    @MethodSource( "comparators" )
    public void compareVeryLargeNumbers(Comparator<String> ouT) {
        assertThat(ouT.compare("1165874568735487968325787328996864", "1165874568735487968325787328996864"), equalTo(0));
        assertThat(ouT.compare("1165874568735487968325787328996864", "1165874568735487968325787328996865"), lessThan(0));
        assertThat(ouT.compare("1165874568735487968325787328996864", "116587456873548796832578732899686"), greaterThan(0));
    }

    @ParameterizedTest
    @DisplayName("Sort smallest negative number first")
    @MethodSource( "comparators" )
    public void sortWithNumbersSmallestNegativeNumberFirst(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("-1", "-2", "-10", "-11", "-100", "1", "2", "10", "11", "100");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort largest negative number first")
    @MethodSource( "comparators" )
    public void sortWithNumbersLargestNegativeNumberFirst(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("-100", "-11", "-10", "-2", "-1", "1", "2", "10", "11", "100");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort negative numbers last")
    @MethodSource( "comparators" )
    public void sortNegativeNumberLast(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("1", "2", "10", "11", "100", "-1", "-2", "-10", "-11", "-100");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }
}
