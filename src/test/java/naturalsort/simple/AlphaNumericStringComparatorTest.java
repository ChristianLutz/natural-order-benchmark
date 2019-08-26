package naturalsort.simple;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author eberry
 */
public class AlphaNumericStringComparatorTest {

    private final AlphaNumericStringComparator ouT = new AlphaNumericStringComparator();

    @Test
    public void simpleNumerics() {
        List<String> myList = Arrays.asList(new String[] {"z1.2.doc", "z1.3.doc", "z1.doc", "z2.doc", "z3.doc",
                "z4.doc", "z5.doc", "z6.doc", "z7.doc", "z8.doc", "z9.doc", "z10.doc", "z11.doc", "z12.doc", "z13.doc",
                "z14.doc", "z15.doc", "z16.doc", "z17.doc", "z18.doc", "z19.doc", "z20.doc", "z100.doc", "z101.doc",
                "z102.doc"
        });
        List<String> unsorted = new ArrayList<>(myList);
        Collections.shuffle(unsorted);
        Collections.sort(unsorted, ouT);

        assertEquals(myList, unsorted);
    }

    @Test
    public void moreComplex() {
        List<String> myList = Arrays.asList(new String[] {
                "10.4X Radonius", "10X Radonius", "20.2X Radonius Prime", "20.5X Radonius", "20X Radonius",
                "20X Radonius Prime", "30.6X Radonius", "30X Radonius", "40.6X Radonius", "40X Radonius",
                "200X Radonius", "200X Radonius", "1000X Radonius Maximus", "Allegia 50 Clasteron",
                "Allegia 51 Clasteron", "Allegia 51B Clasteron", "Allegia 52 Clasteron", "Allegia 60 Clasteron",
                "Allegia 500 Clasteron", "Alpha 2", "Alpha 2A", "Alpha 2A-900", "Alpha 2A-8000", "Alpha 100",
                "Alpha 200", "Callisto Morphamax", "Callisto Morphamax 500", "Callisto Morphamax 500.8",
                "Callisto Morphamax 600", "Callisto Morphamax 600.3", "Callisto Morphamax 700",
                "Callisto Morphamax 700.5", "Callisto Morphamax 5000", "Callisto Morphamax 5000.56",
                "Callisto Morphamax 7000", "Callisto Morphamax 7000 SE", "Callisto Morphamax 7000 SE2",
                "Callisto Morphamax 7000.8", "QRS-60 Intrinsia Machine", "QRS-60F Intrinsia Machine",
                "QRS-62 Intrinsia Machine", "QRS-62F Intrinsia Machine", "Xiph Xlater 5", "Xiph Xlater 40",
                "Xiph Xlater 50", "Xiph Xlater 58", "Xiph Xlater 300", "Xiph Xlater 500", "Xiph Xlater 2000",
                "Xiph Xlater 5000", "Xiph Xlater 10000"
        });

        List<String> unsorted = new ArrayList<>(myList);
        Collections.shuffle(unsorted);
        Collections.sort(unsorted, ouT);

        assertEquals(myList, unsorted);
   }
}
