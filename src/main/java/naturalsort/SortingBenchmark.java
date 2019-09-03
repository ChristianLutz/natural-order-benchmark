package naturalsort;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import net.greypanther.natsort.SimpleNaturalComparator;


@State(Scope.Benchmark)
public class SortingBenchmark {

    private static List<String> input = getInput();

    @Benchmark
    public void smallSet() { // 50
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(input.subList(0, 50), comparator);
    }

    @Benchmark
    public void midSet() { // 500
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(input.subList(0, 500), comparator);
    }

    @Benchmark
    public void largeSet() { // 5000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(input.subList(0, 5000), comparator);
    }

    @Benchmark
    public void xlargeSet() { // 50000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(input.subList(0, 50000), comparator);
    }

    @Benchmark
    public void xxlargeSet() { // 500000
        Comparator<String> comparator = SimpleNaturalComparator.<String>getInstance();
        Collections.sort(input.subList(0, 500000), comparator);
    }

    private static List<String> getInput() {
        List<String> randomStrings = new ArrayList<>(500000);
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .charset(Charset.forName("UTF-8"))
                .stringLengthRange(1, 100);
        EasyRandom easyRandom = new EasyRandom(parameters);
        for (int i = 0; i < 500000; ++i) {
            randomStrings.add(easyRandom.nextObject(String.class));
        }

        return randomStrings;
    }

    public static void main(String[] args) {


        Options opt = new OptionsBuilder()
            .include(".*" + "Set" + ".*")
            .timeUnit(TimeUnit.MILLISECONDS)
            .forks(1)
            .mode(Mode.SingleShotTime)
            .measurementBatchSize(100_000_000)
            .measurementIterations(5)
            .warmupBatchSize(10_000_000)
            .warmupIterations(5)
            .build();
        Runner runner =  new Runner(opt);
        try {
            runner.run();
        } catch (RunnerException e) {
                e.printStackTrace();
        }
    }
}
