package naturalsort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.Ordering;

import naturalsort.dacus.NaturalSorter;
import naturalsort.davekoelle.AlphanumComparator;
import naturalsort.friedrich.Strings;
import naturalsort.paour.NaturalOrderComparator;
import net.greypanther.natsort.SimpleNaturalComparator;

/**
 * This test class has several test to check the different natural sort implementation.
 * The test data are copied from nwoltman (https://github.com/nwoltman/string-natural-compare) who has
 * created some nice test scenarios.
 *
 * @author Christian Lutz (www.kreeloo.de)
 *
 */
public class NWoltmanCaseSensitiveTest {

	public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new NaturalSorter.NaturalComparator(),               // dacus
            new AlphanumComparator(),                            // kolle
            new naturalsort.devexed.NaturalOrderComparator<>(),  // devexed
            Strings.getNaturalComparator(),                      // friedrich
            new NaturalOrderComparator(),                        // paour
            Ordering.natural(),                                  // guava
            SimpleNaturalComparator.getInstance()                // greypanther
        );
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void compareCaseSensitive(Comparator<String> ouT) {
        assertThat(ouT.compare("a", "A"), lessThan(0));
        assertThat(ouT.compare("A", "a"), greaterThan(0));
        assertThat(ouT.compare("b", "C"), lessThan(0));
        assertThat(ouT.compare("C", "b"), greaterThan(0));
        assertThat(ouT.compare("aaa", "Aaa"), lessThan(0));
        assertThat(ouT.compare("Aaa", "aaa"), greaterThan(0));
    }
}
