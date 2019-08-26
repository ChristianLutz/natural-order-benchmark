package naturalsort.dacus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Created by intern on 7/8/15.
 */
public class NaturalSorterTest {

    @Test
    public void testSort() throws Exception {
        List<String> stringList = new ArrayList<>();
        stringList.add("1000X Radonius Maximus");
        stringList.add("10X Radonius");
        stringList.add("200X Radonius");
        stringList.add("20X Radonius");
        stringList.add("20X Radonius Prime");
        stringList.add("30X Radonius");
        stringList.add("40X Radonius");
        stringList.add("Allegia 50 Clasteron");
        stringList.add("Allegia 500 Clasteron");
        stringList.add("Allegia 50B Clasteron");
        stringList.add("Allegia 51 Clasteron");
        stringList.add("Allegia 6R Clasteron");

        stringList = NaturalSorter.sort(stringList);

        assertEquals(Arrays.asList("10X Radonius", "20X Radonius", "20X Radonius Prime", "30X Radonius", "40X Radonius",
           "200X Radonius", "1000X Radonius Maximus", "Allegia 6R Clasteron", "Allegia 50 Clasteron",
           "Allegia 50B Clasteron", "Allegia 51 Clasteron", "Allegia 500 Clasteron"), stringList);
    }
}