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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import naturalsort.dacus.NaturalSorter;
import naturalsort.davekoelle.AlphanumComparator;
import naturalsort.friedrich.Strings;
import naturalsort.paour.NaturalOrderComparator;
import net.greypanther.natsort.CaseInsensitiveSimpleNaturalComparator;

/**
 * This test class has several test to check the different natural sort implementation.
 * The test data are copied from nwoltman (https://github.com/nwoltman/string-natural-compare) who has
 * created some nice test scenarios.
 *
 * @author Christian Lutz (www.kreeloo.de)
 *
 */
public class NWoltmanCaseInsensitiveTest {

    public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new NaturalSorter.NaturalComparator(),               // dacus
            new AlphanumComparator(),                            // kolle
            new naturalsort.devexed.NaturalOrderComparator<>(),  // devexed
            Strings.getNaturalComparator(),                      // friedrich
            new NaturalOrderComparator(),                        // paour
            new org.padler.natorder.NaturalOrderComparator(),    // padler improved pour
            Comparator.naturalOrder(),                           // java.util
            CaseInsensitiveSimpleNaturalComparator.getInstance() // greypanther
        );
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareStringWithoutNumber(Comparator<String> ouT) {
        assertThat(ouT.compare("a", "a"), equalTo(0));
        assertThat(ouT.compare("a", "a"), equalTo(0));
        assertThat(ouT.compare("a", "b"), lessThan(0));
        assertThat(ouT.compare("b", "a"), greaterThan(0));
        assertThat(ouT.compare("a", "aa"), lessThan(0));
        assertThat(ouT.compare("aa", "a"), greaterThan(0));
        assertThat(ouT.compare("a", "ba"), lessThan(0));
        assertThat(ouT.compare("ba", "a"), greaterThan(0));
        assertThat(ouT.compare("aa", "b"), lessThan(0));
        assertThat(ouT.compare("b", "aa"), greaterThan(0));
        assertThat(ouT.compare("aa", "ba"), lessThan(0));
        assertThat(ouT.compare("ba", "aa"), greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareIntegerSubStringByTheirNumericValue(Comparator<String> ouT) {
        assertThat(ouT.compare("1", "1"), equalTo(0));
        assertThat(ouT.compare("50", "50"), equalTo(0));
        assertThat(ouT.compare("11001", "1102"), greaterThan(0));
        assertThat(ouT.compare("a", "a1"), lessThan(0));
        assertThat(ouT.compare("a1", "a"), greaterThan(0));
        assertThat(ouT.compare("1", "a"), lessThan(0));
        assertThat(ouT.compare("a", "1"), greaterThan(0));
        assertThat(ouT.compare("2", "3"), lessThan(0));
        assertThat(ouT.compare("3", "2"), greaterThan(0));
        assertThat(ouT.compare("2", "10"), lessThan(0));
        assertThat(ouT.compare("10", "2"), greaterThan(0));
        assertThat(ouT.compare("a1", "a1"), equalTo(0));
        assertThat(ouT.compare("a1", "a2"), lessThan(0));
        assertThat(ouT.compare("a2", "a1"), greaterThan(0));
        assertThat(ouT.compare("a1", "a11"), lessThan(0));
        assertThat(ouT.compare("a11", "a1"), greaterThan(0));
        assertThat(ouT.compare("a11", "a12"), lessThan(0));
        assertThat(ouT.compare("a12", "a11"), greaterThan(0));
        assertThat(ouT.compare("a1", "a1a"), lessThan(0));
        assertThat(ouT.compare("a1a", "a1"), greaterThan(0));
        assertThat(ouT.compare("a1a", "a11"), lessThan(0));
        assertThat(ouT.compare("a11", "a1a"), greaterThan(0));
        assertThat(ouT.compare("a1a", "a11a"), lessThan(0));
        assertThat(ouT.compare("a11a", "a1a"), greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareWithZeroInTheString(Comparator<String> ouT) {
        assertThat(ouT.compare("a00", "a000"), lessThan(0));
        assertThat(ouT.compare("a 0 a", "a 0 b"), lessThan(0));
        assertThat(ouT.compare("a 0 a", "a 00 b"), lessThan(0));
        assertThat(ouT.compare("a0a", "a0b"), lessThan(0));
        assertThat(ouT.compare("a0a", "a00b"), lessThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareIntegerSubstringsWithLeadingZeros(Comparator<String> ouT) {
        assertThat(ouT.compare("000", "000"), equalTo(0));
        assertThat(ouT.compare("001", "001"), equalTo(0));
        assertThat(ouT.compare("00", "1"), lessThan(0));
        assertThat(ouT.compare("00", "0001"), lessThan(0));
        assertThat(ouT.compare("010", "01"), greaterThan(0));
        assertThat(ouT.compare("010", "001"), greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareShouldNotConsiderFloatingPoint(Comparator<String> ouT) {
        // should not consider a decimal point surrounded by integers as a floating point number
        assertThat(ouT.compare("0.01", "0.001"), lessThan(0));
        assertThat(ouT.compare("0.001", "0.01"), greaterThan(0));
        assertThat(ouT.compare("1.01", "1.001"), lessThan(0));
        assertThat(ouT.compare("1.001", "1.01"), greaterThan(0));
    }


    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareVeryLargeNumbers(Comparator<String> ouT) {
        assertThat(ouT.compare("1165874568735487968325787328996864", "1165874568735487968325787328996864"), equalTo(0));
        assertThat(ouT.compare("1165874568735487968325787328996864", "1165874568735487968325787328996865"), lessThan(0));
        assertThat(ouT.compare("1165874568735487968325787328996864", "116587456873548796832578732899686"), greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void testNaturalSortWithUpperLowerCaseStrings(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("a", "b", "c", "d");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void testNaturalSortWithDifferentFileNames(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("file-1.txt", "file-2.txt", "file-3.txt", "file-20.txt");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void testNaturalSortWithDifferentFileNames2(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("'a0", "a00", "a000", "a000000000", "a00.html", "a000.html",
                "a00a.html", "a000a.html", "a000b.html", "a1a.html", "a001a.html", "a0001a.html");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void testNaturalSortWithNumbers(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("-1", "-2", "-10", "-11", "-100", "1", "2", "10", "11", "100");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }
}
