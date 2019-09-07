package naturalsort;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.padler.natorder.NaturalOrderComparator;

import naturalsort.berry.AlphaNumericStringComparator;
import naturalsort.dacus.NaturalSorter;
import naturalsort.friedrich.Strings;
import naturalsort.koelle.AlphanumComparator;
import net.greypanther.natsort.SimpleNaturalComparator;


@State(Scope.Benchmark)
public class SortingBenchmark {

    private static List<String> input50 = getInput(50);
    private static List<String> input500 = getInput(500);
    private static List<String> input5000 = getInput(5000);
    private static List<String> input50000 = getInput(50000);
    private static List<String> input500000 = getInput(500000);

    @Benchmark
    public void padler_smallSet() { // 50
        Comparator<String> comparator = new NaturalOrderComparator();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void padler_midSet() { // 500
        Comparator<String> comparator = new NaturalOrderComparator();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void padler_largeSet() { // 5000
        Comparator<String> comparator = new NaturalOrderComparator();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void padler_xlargeSet() { // 50000
        Comparator<String> comparator = new NaturalOrderComparator();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void padler_xxlargeSet() { // 500000
        Comparator<String> comparator = new NaturalOrderComparator();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void dacus_smallSet() { // 50
        Comparator<String> comparator = new NaturalSorter.NaturalComparator();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void dacus_midSet() { // 500
        Comparator<String> comparator = new NaturalSorter.NaturalComparator();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void dacus_largeSet() { // 5000
        Comparator<String> comparator = new NaturalSorter.NaturalComparator();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void dacus_xlargeSet() { // 50000
        Comparator<String> comparator = new NaturalSorter.NaturalComparator();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void dacus_xxlargeSet() { // 500000
        Comparator<String> comparator = new NaturalSorter.NaturalComparator();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void devexed_smallSet() { // 50
        Comparator<String> comparator = new naturalsort.devexed.NaturalOrderComparator<>();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void devexed_midSet() { // 500
        Comparator<String> comparator = new naturalsort.devexed.NaturalOrderComparator<>();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void devexed_largeSet() { // 5000
        Comparator<String> comparator = new naturalsort.devexed.NaturalOrderComparator<>();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void devexed_xlargeSet() { // 50000
        Comparator<String> comparator = new naturalsort.devexed.NaturalOrderComparator<>();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void devexed_xxlargeSet() { // 500000
        Comparator<String> comparator = new naturalsort.devexed.NaturalOrderComparator<>();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void koelle_smallSet() { // 50
        Comparator<String> comparator = new AlphanumComparator();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void koelle_midSet() { // 500
        Comparator<String> comparator = new AlphanumComparator();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void koelle_largeSet() { // 5000
        Comparator<String> comparator = new AlphanumComparator();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void koelle_xlargeSet() { // 50000
        Comparator<String> comparator = new AlphanumComparator();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void koelle_xxlargeSet() { // 500000
        Comparator<String> comparator = new AlphanumComparator();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void friedrich_smallSet() { // 50
        Comparator<String> comparator = Strings.getNaturalComparatorAscii();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void friedrich_midSet() { // 500
        Comparator<String> comparator = Strings.getNaturalComparatorAscii();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void friedrich_largeSet() { // 5000
        Comparator<String> comparator = Strings.getNaturalComparatorAscii();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void friedrich_xlargeSet() { // 50000
        Comparator<String> comparator = Strings.getNaturalComparatorAscii();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void friedrich_xxlargeSet() { // 500000
        Comparator<String> comparator = Strings.getNaturalComparatorAscii();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void panther_smallSet() { // 50
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void panther_midSet() { // 500
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void panther_largeSet() { // 5000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void panther_xlargeSet() { // 50000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void panther_xxlargeSet() { // 500000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    @Benchmark
    public void berry_smallSet() { // 50
        Comparator<String> comparator = new AlphaNumericStringComparator();
        Collections.sort(new ArrayList<>(input50), comparator);
    }

    @Benchmark
    public void berry_midSet() { // 500
        Comparator<String> comparator = new AlphaNumericStringComparator();
        Collections.sort(new ArrayList<>(input500), comparator);
    }

    @Benchmark
    public void berry_largeSet() { // 5000
        Comparator<String> comparator = new AlphaNumericStringComparator();
        Collections.sort(new ArrayList<>(input5000), comparator);
    }

    @Benchmark
    public void berry_xlargeSet() { // 50000
        Comparator<String> comparator = new AlphaNumericStringComparator();
        Collections.sort(new ArrayList<>(input50000), comparator);
    }

    @Benchmark
    public void berry_xxlargeSet() { // 500000
        Comparator<String> comparator = new AlphaNumericStringComparator();
        Collections.sort(new ArrayList<>(input500000), comparator);
    }


    private static List<String> getInput(int size) {
        List<String> randomStrings = new ArrayList<>(size);
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .charset(Charset.forName("UTF-8"))
                .stringLengthRange(1, 100);
        EasyRandom easyRandom = new EasyRandom(parameters);
        for (int i = 0; i < size; ++i) {
            randomStrings.add(easyRandom.nextObject(String.class));
        }

        return randomStrings;
    }

    public static void main(String[] args) {
        System.out.println("Use main");
        Options opt = new OptionsBuilder()
            .include(".*" + "Set" + ".*")
//            .timeUnit(TimeUnit.MILLISECONDS)
            .forks(1)
//            .mode(Mode.Throughput)
//            .measurementBatchSize(100_000_000)
            .measurementIterations(3)
//            .warmupBatchSize(10_000_000)
            .warmupIterations(2)
            .build();
        Runner runner =  new Runner(opt);
        try {
            runner.run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
