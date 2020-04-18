package test;

import com.canftin.Tuple2;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Tuple2Test {

    @Test
    public void hasTwoSlots() {
        Tuple2<String, Integer> stringIntTuple = new Tuple2<String, Integer>("foo", 1);
        Tuple2<String, Integer> stringIntTuple2 = Tuple2.tuple("foo", 1);
        assertThat(stringIntTuple._1, is("foo"));
        assertThat(stringIntTuple._2, is(1));
        assertThat(stringIntTuple2._1, is("foo"));
        assertThat(stringIntTuple2._2, is(1));
    }
}
