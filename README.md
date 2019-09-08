# naturalsort

In most application natural ordering is more and more common. But there is not one common definition of what 
natural order is. If you look at [wikipedia][11] you'll find a simple definition. 
> Natural sort order is an ordering of strings in alphabetical order, except that multi-digit numbers are ordered as a single character

It means k2 < k10. But a lot of possibilities aren't defined. So what is happening with negative numbers 
e.g. k-2 ? k-10 will it be represented as minus or as a dash.

Another definition from [rosettacode][12]. 
> 1. Ignore leading, trailing and multiple adjacent spaces
> 2. Make all whitespace characters equivalent.
> 3. Sorting without regard to case.
> 4. Sorting numeric portions of strings in numeric order.
> 5. ...

The questions are 
* what java implementations are available
* what feature do they support
* how fast are they.


### Implementations

- berry - [AlphaNumericStringComperator][4]
- dacus - [NaturalSorter][3]
- devexed - [NaturalOrderComparator][14]
- friedrich - [Strings][6] had been original been posted [here][7] but this side isn't available anymore. But it is still [archived][8] 
- koelle - [AlphanumComparator][5]
- padler - [NaturalOrderComparator][2]
- panther - [java-nat-sort][9]


## Features

To test what features are supported and if they are working well, I created a set of unit tests. 
The starting point of the tests are the work of [Nathan Woltman][13] he has done for his [string-natural-compare][1].
Also most of the implementations have a test sets. So I copied them and checked if this set do also work for the other
implementations.

 features| padler | dacus | devexed | keolle | friedrich | panther | berry 
---------|--------|-------|---------|--------|-----------|---------|-------
natural order of numbers | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: 
ignore decimal point | :white_check_mark: | :x: | :x: | :white_check_mark: | :white_check_mark: | :x: | :x:
local specific decimal point | :x: | :x: | :white_check_mark: | :x: | :x: | :x: | :x: 
handle leading zeros | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: 
large numbers | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x:
order of negativ numbers | smallest negative number first | smallest negative number first | **largest** negative number first | smallest negative number first | smallest negative number first | smallest negative number first | smallest negative number first 
| | | | | | | |
case sensitive ASCII | :white_check_mark: | :x: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: 
case insensitive ASCII | :x: | :x: | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :x:
ignore leading and trailing whitespaces  | :x: | :x: | :white_check_mark: | :x: | :x: | :x: | 
support java.text.Collator  | | | | | | | 
Unicode support | | | | | | | 

### Notes
The problem with the implemenations are that they are mostly hardcoded and don't provide much configuration option. So if you need some specific natural order you may have to stick with one implementation. Or you even need to implement it by your self.

Dacus has some problems with the order in both cases `case sensitive` and `case insenstive` that is the reason why both are red.


## Performance

The test is based on [JMH][15]. The test string have been created via [EasyRandom][16] with string length between 1 and 100. 

The benchmark is using the comperator within `Collection.sort`. The sort itself has `O(n*log(n))`. This should be the major performance difference. 

To execute the benchmark you first need to build the jar with `mvn clean install -DskipTests` and then run `java -jar target/naturalsort-1.0.0-SNAPSHOT`

 test size| padler | dacus | devexed | keolle | friedrich | panther | berry 
---------|--------|-------|---------|--------|-----------|---------|-------
50 | 322233,394 ± 23164,234 ops/s | 1669,788 ± 2144,115 ops/s | 65,350 ± 28,982 ops/s | 12639,356 ± 340,300 ops/s | 351708,520 ± 17678,003 ops/s | 379094,168 ± 28208,360 ops/s | 1539,858 ± 194,941 ops/s
500 | 13363,359 ± 1086,943 ops/s | 92,728 ± 30,003 ops/s | 3,497 ± 0,694 ops/s | 720,988 ± 67,330 ops/s | 13814,737 ± 852,784 ops/s  | 14819,162 ± 1340,082 ops/s | 84,747 ± 0,707 ops/s
5000 | 785,413 ± 40,206 ops/s | 6,320 ± 3,476 ops/s | 0,246 ± 0,220 ops/s | 48,067 ± 3,274 ops/s | 861,733 ± 18,264 ops/s | 884,419 ± 29,256 ops/s | 5,951 ± 0,240 ops/s
50000 | 50,611 ± 22,134 ops/s | 0,469 ± 0,026 ops/s | 0,018 ± 0,013 ops/s | 3,595 ± 0,135 ops/s | 54,059 ± 42,570 ops/s | 58,565 ± 4,214 ops/s | 0,443 ± 0,120 ops/s
500000 | 2,543 ± 0,259 ops/s | 0,037 ± 0,007 ops/s | 0,002 ± 0,001 ops/s | 0,275 ± 0,004 ops/s | 2,930 ± 0,235 ops/s | 3,204 ± 0,229 ops/s | 0,035 ± 0,016 ops/s

JMH Benchmark configuration

```
# JMH version: 1.21
# VM version: JDK 11.0.4, OpenJDK 64-Bit Server VM, 11.0.4+11
# VM invoker: /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/java
# VM options: <none>
# Warmup: 2 iterations, 10 s each
# Measurement: 3 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
```

[1]: https://github.com/nwoltman/string-natural-compare
[2]: https://github.com/616slayer616/natorder
[3]: https://github.com/Dacus/NaturalSort
[4]: http://simplesql.tigris.org/servlets/ProjectDocumentList?folderID=0
[5]: http://www.davekoelle.com/alphanum.html
[6]: https://github.com/marcboon/FlashCards
[7]: http://weblogs.java.net/blog/skelvin/archive/2006/01/natural_string.html
[8]: https://web.archive.org/web/20071010144420/http://weblogs.java.net/blog/skelvin/archive/2006/01/natural_string.html
[9]: https://github.com/gpanther/java-nat-sort
[10]: https://docs.oracle.com/javase/9/docs/api/java/util/Comparator.html?is-external=true#naturalOrder--
[11]: https://en.wikipedia.org/wiki/Natural_sort_order
[12]: https://rosettacode.org/wiki/Natural_sorting
[13]: https://github.com/nwoltman
[14]: https://github.com/devexed/natural-sort
[15]: http://openjdk.java.net/projects/code-tools/jmh/
[16]: https://github.com/j-easy/easy-random