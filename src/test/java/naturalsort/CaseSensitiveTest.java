package naturalsort;

import static org.hamcrest.MatcherAssert.assertThat;
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

import naturalsort.berry.AlphaNumericStringComparator;
import naturalsort.dacus.NaturalSorter;
import naturalsort.friedrich.Strings;
import naturalsort.koelle.AlphanumComparator;
import net.greypanther.natsort.SimpleNaturalComparator;

/**
 * This test class has several test to check the different natural sort implementation.
 * The test data are copied from nwoltman (https://github.com/nwoltman/string-natural-compare) who has
 * created some nice test scenarios.
 *
 * @author Christian Lutz (www.kreeloo.de)
 *
 */
public class CaseSensitiveTest {

    public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new AlphaNumericStringComparator(),                             // berry
            new NaturalSorter.NaturalComparator(),                          // dacus
            new naturalsort.devexed.NaturalOrderComparator<String>(),       // devexed
            Strings.getNaturalComparatorAscii(),                            // friedrich
            new AlphanumComparator(),                                       // kolle
            new NaturalOrderComparator(),                                   // padler improved pour
            SimpleNaturalComparator.<String>getInstance()                   // panther
        );
    }

    @ParameterizedTest
    @DisplayName("Sort uppercase before lowercase")
    @MethodSource( "comparators" )
    public void compareCaseSensitive(Comparator<String> ouT) {
        assertThat(ouT.compare("A", "a"), lessThan(0));
        assertThat(ouT.compare("a", "A"), greaterThan(0));
        assertThat(ouT.compare("C", "b"), lessThan(0));
        assertThat(ouT.compare("b", "C"), greaterThan(0));
        assertThat(ouT.compare("Aaa", "aaa"), lessThan(0));
        assertThat(ouT.compare("aaa", "Aaa"), greaterThan(0));
    }

    @ParameterizedTest
    @DisplayName("Sort uppercase before lowercase 2")
    @MethodSource( "comparators" )
    public void testNaturalSortWithUpperLowerCaseStringsWithLowerCaseFirst(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("B", "C", "a", "d");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort first by case (upper) then by number")
    @MethodSource( "comparators" )
    public void sortDifferentFileNamesWithNumbers(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("File-2.txt", "File-10.txt", "File-100.txt", "file-1.txt", "file-20.txt");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }

    @ParameterizedTest
    @DisplayName("Sort numbers before characters with number")
    @MethodSource( "comparators" )
    public void sortNumbersBeforeCaseInsensitiveWithNumber(Comparator<String> ouT) {
        final List<String> testInput = Arrays.asList("1", "2", "100", "A1", "C2", "C12", "a", "b", "b1", "c", "c10");
        final List<String> list = new ArrayList<>(testInput);
        Collections.shuffle(list);
        Collections.sort(list, ouT);
        assertEquals(testInput, list);
    }
}
