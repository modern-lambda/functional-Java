package test;

import com.canftin.Iterables.FilteringIterable;
import com.canftin.Predicate;
import org.junit.Test;

import java.util.ArrayList;

import static com.canftin.Iterables.FilteringIterable.filter;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static test.IterableMatcher.iterates;

public class FilteringIterableTest {

    @Test
    public void filtersElementsUsingPredicate() {
        Iterable<String> stooges = asList("Moe", "Larry", "Curly");
        FilteringIterable<String> shortNames = filter(new Predicate<String>() {
            @Override
            public Boolean apply(String s) {
                return s.length() == 3;
            }
        }, stooges);

        assertThat(shortNames, iterates("Moe"));
    }


    @Test
    public void returnsEmptyIterableIfPredicateFailedForAllElements() {
        Iterable<Integer> odds = asList(1, 3, 5);
        FilteringIterable<Integer> evens = filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer integer) {
                return integer % 2 == 0;
            }
        }, odds);

        assertThat(evens, IterableMatcher.<Integer>iterates());
    }

    @Test
    public void filtersNothingFromEmptyList() {
        FilteringIterable<Integer> quotientsOfZero = filter(new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer integer) {
                return integer / 0 == 0;
            }
        }, new ArrayList<Integer>());

        assertThat(quotientsOfZero, IterableMatcher.<Integer>iterates());
    }
}
