package com.canftin.Iterables;

import com.canftin.DyadicFunction;
import com.canftin.Tuple2;

import java.util.Iterator;

import static com.canftin.Tuple2.tuple;

public class ZippingIterable<A, B, C> implements Iterable<C> {

    // a -> b -> c
    private final DyadicFunction<? super A, ? super B, ? extends C> zipper;
    private final Iterable<A>                                       as;
    private final Iterable<B>                                       bs;

    public ZippingIterable(DyadicFunction<? super A, ? super B, ? extends C> zipper, Iterable<A> as,
                           Iterable<B> bs) {
        this.zipper = zipper;
        this.as = as;
        this.bs = bs;
    }

    @Override
    public Iterator<C> iterator() {
        final Iterator<A> asIterator = as.iterator();
        final Iterator<B> bsIterator = bs.iterator();
        return new ImmutableIterator<C>() {
            @Override
            public boolean hasNext() {
                return asIterator.hasNext() && bsIterator.hasNext();
            }

            @Override
            public C next() {
                return zipper.apply(asIterator.next(), bsIterator.next());
            }
        };
    }

    /**
     * (a -> b -> c) -> [a] -> [b] -> [c]
     * @param zipper
     * @param as
     * @param bs
     * @param <A>
     * @param <B>
     * @param <C>
     * @return
     */
    public static <A, B, C> ZippingIterable<A, B, C> zipWith(DyadicFunction<? super A, ? super B, ? extends C> zipper,
                                                             Iterable<A> as,
                                                             Iterable<B> bs) {
        return new ZippingIterable<A, B, C>(zipper, as, bs);
    }

    /**
     * [a] -> [b] -> [(a,b)]
     * @param as
     * @param bs
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> ZippingIterable<A, B, Tuple2<A, B>> zip(Iterable<A> as, Iterable<B> bs) {
        return new ZippingIterable<A, B, Tuple2<A, B>>(
                new DyadicFunction<A, B, Tuple2<A, B>>() {
                    @Override
                    public Tuple2<A, B> apply(A a, B b) {
                        return tuple(a, b);
                    }
                },
                as,
                bs
        );
    }
}
