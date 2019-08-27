package naturalsort.paour;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;


public class NaturalOrderComparatorTest {

    private NaturalOrderComparator naturalOrderComparator = new NaturalOrderComparator();

    private List<String> sorted = Arrays.asList("1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01",
            "pic2", "pic02", "pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic05",
            "pic 5 something", "pic 6", "pic   7", "pic100", "pic100a", "pic120", "pic121",
            "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08", "x8-y8");
    private List<String> input = Arrays.asList("1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01",
            "pic2", "pic02", "pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic05",
            "pic 5 something", "pic 6", "pic   7", "pic100", "pic100a", "pic120", "pic121",
            "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08", "x8-y8");

    @Test
    void simple() {
        List<String> unSorted = Arrays.asList("2", "3", "1");
        List<String> sorted = Arrays.asList("1", "2", "3");

        unSorted.sort(naturalOrderComparator);

        assertThat(unSorted, is(sorted));
    }

    @Test
    void simpleWithChars() {
        List<String> unSorted = Arrays.asList("2", "3", "1B", "1A", "1");
        List<String> sorted = Arrays.asList("1", "1A", "1B", "2", "3");

        unSorted.sort(naturalOrderComparator);

        assertThat(unSorted, is(sorted));
    }

    @Test
    void sortHouseNumbers() {
        List<String> input = Arrays.asList("A", "1007A", "1A", "1B", "2A", "8052A", "2165A", "4472A", "2500A", "4604A");
        List<String> sorted = Arrays.asList("1A", "1B", "2A", "1007A", "2165A", "2500A", "4472A", "4604A", "8052A", "A");
        input.sort(naturalOrderComparator);

        assertThat(input, is(sorted));
    }

    @Test
    void defaultTest() {
        input.sort(naturalOrderComparator);

        assertThat(input, is(sorted));
    }

    @Test
    void scramble42() {
        Collections.shuffle(input, new Random(42));

        input.sort(naturalOrderComparator);

        assertThat(input, is(sorted));
    }

    @Test
    void scramble420() {
        Collections.shuffle(input, new Random(420));

        input.sort(naturalOrderComparator);

        assertThat(input, is(sorted));
    }

    @Test
    void scramble1337() {
        Collections.shuffle(input, new Random(1337));

        input.sort(naturalOrderComparator);

        assertThat(input, is(sorted));
    }

    @Test
    void differentLengthEquality() {
        int compare = naturalOrderComparator.compare("1-2", "1-02");
        int compare2 = naturalOrderComparator.compare("1-02", "1-2");

        assertTrue(compare < 0);
        assertTrue(compare2 > 0);
    }

    @Test
    void sameLengthEquality() {
        int compare = naturalOrderComparator.compare("pic 5", "pic05");
        int compare2 = naturalOrderComparator.compare("pic05", "pic 5");

        assertTrue(compare < 0);
        assertTrue(compare2 > 0);
    }

    @Test
    void zero() {
        int compare = naturalOrderComparator.compare("pic010", "pic 010");
        int compare2 = naturalOrderComparator.compare("pic 010", "pic010");

        assertTrue(compare < 0);
        assertTrue(compare2 > 0);
    }

    @Test
    void floats() {
        List<String> unSorted = Arrays.asList("0.9", "1.0c", "1.2", "1.3", "0.6", "1.1", "0.7", "0.3", "1.0b", "1.0", "0.8");
        List<String> sorted = Arrays.asList("0.3", "0.6", "0.7", "0.8", "0.9", "1.0", "1.0b", "1.0c", "1.1", "1.2", "1.3");

        unSorted.sort(naturalOrderComparator);

        assertThat(unSorted, is(sorted));
    }

    @Test
    void floatsWithCommas() {
        List<String> unSorted = Arrays.asList("0,9", "1,0c", "1,2", "1,3", "0,6", "1,1", "0,7", "0,3", "1,0b", "1,0", "0,8");
        List<String> sorted = Arrays.asList("0,3", "0,6", "0,7", "0,8", "0,9", "1,0", "1,0b", "1,0c", "1,1", "1,2", "1,3");

        unSorted.sort(naturalOrderComparator);

        assertThat(unSorted, is(sorted));
    }
}
