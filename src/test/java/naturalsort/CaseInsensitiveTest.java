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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.padler.natorder.NaturalOrderComparator;

import naturalsort.dacus.NaturalSorter;
import naturalsort.friedrich.Strings;
import naturalsort.koelle.AlphanumComparator;
import net.greypanther.natsort.CaseInsensitiveSimpleNaturalComparator;

/**
 * This test class has several test to check the different natural sort implementation.
 * The test data are copied from nwoltman (https://github.com/nwoltman/string-natural-compare) who has
 * created some nice test scenarios.
 *
 * @author Christian Lutz (www.kreeloo.de)
 *
 */
public class CaseInsensitiveTest {

    public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new NaturalSorter.NaturalComparator(),                         // dacus
            new AlphanumComparator(),                                      // kolle
            new naturalsort.devexed.NaturalOrderComparator<String>(),      // berry
            Strings.getNaturalComparatorIgnoreCaseAscii(),                 // friedrich
            new NaturalOrderComparator(),                                  // padler improved pour
            Comparator.<String>naturalOrder(),                             // java.util
            CaseInsensitiveSimpleNaturalComparator.<String>getInstance()   // greypanther
        );
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareStringWithoutNumber(Comparator<String> ouT) {
        assertThat(ouT.compare("a", "a"), equalTo(0));
        assertThat(ouT.compare("a", "A"), equalTo(0));
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
    @DisplayName("Sort single number before character")
    @MethodSource( "comparators" )
    public void compareIntegerSubStringByTheirNumericValue(Comparator<String> ouT) {
        assertThat(ouT.compare("a", "a1"), lessThan(0));
        assertThat(ouT.compare("a1", "a"), greaterThan(0));
        assertThat(ouT.compare("1", "a"), lessThan(0));
        assertThat(ouT.compare("a", "1"), greaterThan(0));
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
        assertThat(ouT.compare("a000", "a00"), greaterThan(0));
        assertThat(ouT.compare("a 0 a", "a 0 b"), lessThan(0));
        assertThat(ouT.compare("a 0 b", "a 0 a"), greaterThan(0));
        assertThat(ouT.compare("a 0 a", "a 00 b"), lessThan(0));
        assertThat(ouT.compare("a 00 b", "a 0 a"), greaterThan(0));
        assertThat(ouT.compare("a0a", "a0b"), lessThan(0));
        assertThat(ouT.compare("a0b", "a0a"), greaterThan(0));
        assertThat(ouT.compare("a0a", "a00b"), lessThan(0));
        assertThat(ouT.compare("a00b", "a0a"), greaterThan(0));
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void testNaturalSortWithUpperLowerCaseStrings(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("a", "B", "C", "d");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort case insensitive and number")
    @MethodSource( "comparators" )
    public void sortCaseInsensitiveAndNumber(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("a", "A1", "b", "b1", "c", "C2", "c10");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort file names case insensitive and number")
    @MethodSource( "comparators" )
    public void testNaturalSortWithDifferentFileNames(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("file-1.txt", "File-2.txt", "File-3.txt", "file-20.txt");
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
}
