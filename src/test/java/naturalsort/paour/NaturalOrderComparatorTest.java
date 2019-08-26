package naturalsort.paour;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;


public class NaturalOrderComparatorTest {

    private static final List<String> strings = Arrays.asList(new String[] { "1-2", "1-02", "1-20", "10-20", "fred",
            "jane", "pic01", "pic2", "pic02", "pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic 5", "pic05",
            "pic 5 something", "pic 6", "pic   7", "pic100", "pic100a", "pic120", "pic121",
            "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08", "x8-y8" });

    private final Comparator<String> ouT = new NaturalOrderComparator();

	@Test
    public void simpleTest() {
        List<String> scrambled = new ArrayList<>(strings);
        Collections.shuffle(scrambled);
        Collections.sort(scrambled, ouT);

        assertEquals(strings, scrambled);
    }

    @Test
    public void shuffle3000() {
        List<String> scrambled = new ArrayList<>(strings);
        Collections.shuffle(scrambled, new Random(3000));
        Collections.sort(scrambled, ouT);

        assertEquals(strings, scrambled);
    }

    @Test
    public void notSorted() {
        List<String> scrambled = new ArrayList<>(strings);
        Collections.shuffle(scrambled, new Random(3000));
        assertNotEquals(strings, scrambled);
    }

	@Test
    public void compareSymmetric() {
        assertEquals(-1, ouT.compare("1-2", "1-02"));
        assertEquals(1, ouT.compare("1-02", "1-2"));
        assertThat(ouT.compare("pic 5", "pic05"), lessThan(0));
        assertThat(ouT.compare("pic05", "pic 5"), greaterThan(0));
    }

	@Test
    public void floatsWithCommas() {
        List<String> unSorted = Arrays
            .asList("0.9", "1.0c", "1.2", "1.3", "0.6", "1.1", "0.7", "0.3", "1.0b", "1.0", "0.8");

        unSorted.sort(ouT);

        assertEquals(Arrays
            .asList("0.3", "0.6", "0.7", "0.8", "0.9", "1.0", "1.0b", "1.0c", "1.1", "1.2", "1.3"), unSorted);
    }
}
