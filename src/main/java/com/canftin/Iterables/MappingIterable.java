package com.canftin.Iterables;

import com.canftin.MonadicFunction;

import java.util.Iterator;

public class MappingIterable<A, B> implements Iterable<B> {

    // a -> b
    private final MonadicFunction<? super A, ? extends B> function;
    private final Iterable<A>                             as;

    public MappingIterable(MonadicFunction<? super A, ? extends B> function, Iterable<A> as) {
        this.function = function;
        this.as = as;
    }

    @Override
    public Iterator<B> iterator() {
        final Iterator<A> asIterator = as.iterator();

        return new ImmutableIterator<B>() {
            @Override
            public boolean hasNext() {
                return asIterator.hasNext();
            }

            @Override
            public B next() {
                return function.apply(asIterator.next());
            }
        };
    }

    /**
     * (a -> b) -> [a] -> [b]
     * @param function
     * @param as
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> MappingIterable<A, B> map(
            MonadicFunction<? super A, ? extends B> function,
            Iterable<A> as) {
        return new MappingIterable<A, B>(function, as);
    }
}
