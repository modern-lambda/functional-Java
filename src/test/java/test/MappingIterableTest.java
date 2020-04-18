package test;

import com.canftin.Iterables.MappingIterable;
import com.canftin.MonadicFunction;
import org.junit.Test;

import static com.canftin.Iterables.MappingIterable.map;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static test.IterableMatcher.iterates;

public class MappingIterableTest {
    @Test
    public void mapsInputsIntoOutputs() {
        Iterable<String> strings = asList("one", "two", "three");
        MappingIterable<String, Integer> stringsToLengths = map(new MonadicFunction<String, Integer>() {
            @Override
            public Integer apply(String string) {
                return string.length();
            }
        }, strings);

        assertThat(stringsToLengths, iterates(3, 3, 5));
    }
}
