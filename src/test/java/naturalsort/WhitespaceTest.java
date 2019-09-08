package naturalsort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.padler.natorder.NaturalOrderComparator;

import naturalsort.berry.AlphaNumericStringComparator;
import naturalsort.dacus.NaturalSorter;
import naturalsort.friedrich.Strings;
import naturalsort.koelle.AlphanumComparator;
import net.greypanther.natsort.CaseInsensitiveSimpleNaturalComparator;

public class WhitespaceTest {

    public static Collection<Comparator<String>> comparators() {
        return Arrays.asList(
            new AlphaNumericStringComparator(),                            // berry
            new NaturalSorter.NaturalComparator(),                         // dacus
            new naturalsort.devexed.NaturalOrderComparator<String>(),      // devexed
            Strings.getNaturalComparatorIgnoreCaseAscii(),                 // friedrich
            new AlphanumComparator(),                                      // kolle
            new NaturalOrderComparator(),                                  // padler improved pour
            CaseInsensitiveSimpleNaturalComparator.<String>getInstance()   // panther
        );
    }

    @ParameterizedTest
    @MethodSource( "comparators" )
    public void ignoreLeadingTrailingWhitespaces(Comparator<String> ouT) {
        assertThat(ouT.compare("a", " a"), equalTo(0));
        assertThat(ouT.compare("a", "a "), equalTo(0));
        assertThat(ouT.compare("a", "   a"), equalTo(0));
        assertThat(ouT.compare("a", "a   "), equalTo(0));
        assertThat(ouT.compare("a", "\r\na"), equalTo(0));
        assertThat(ouT.compare("a", "a\r\n"), equalTo(0));
        assertThat(ouT.compare("a", "   a   "), equalTo(0));
    }
}
