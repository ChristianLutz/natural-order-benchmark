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
natural order of numbers | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: 
ignore decimal point | :white_check_mark: | :x: | :x: | :white_check_mark: | :white_check_mark: | :x: | :x:
local specific decimal point | :x: | :x: | :white_check_mark: | :x: | :x: | :x: | :x: 
handle leading zeros | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: 
large numbers | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x:
order of negativ numbers | smallest negative number first | smallest negative number first | **largest** negative number first | smallest negative number first | smallest negative number first | smallest negative number first | smallest negative number first 
| | | | | | | |
case sensitive ASCII | :white_check_mark: | :x: | :x: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: 
case insensitive ASCII | :x: | :x: | :white_check_mark: | :x: | :white_check_mark: | :white_check_mark: | :x:
ignore leading and trailing whitespaces  | | | | | | | 
support java.text.Collator  | | | | | | | 
Unicode support | | | | | | | 

### Notes
The problem with the implemenations are that they are mostly hardcoded and don't provide much configuration option. So if you need some specific natural order you may have to stick with one implementation. Or you even need to implement it by your self.

## Performance



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