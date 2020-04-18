package com.canftin.Iterables;

import com.canftin.MonadicFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteringIterable<A> implements Iterable<A> {

    // a -> Bool
    private final MonadicFunction<? super A, Boolean> predicate;
    private final Iterable<A>                         as;

    public FilteringIterable(MonadicFunction<? super A, Boolean> predicate, Iterable<A> as) {
        this.predicate = predicate;
        this.as = as;
    }

    @Override
    public Iterator<A> iterator() {
        final Iterator<A> asIterator = as.iterator();

        return new ImmutableIterator<A>() {
            private A queued;

            @Override
            public boolean hasNext() {
                return nextElementIsAlreadyQueued() || queuedAnotherElement();
            }

            @Override
            public A next() {
                if (hasNext())
                    return dequeueNextElement();

                throw new NoSuchElementException();
            }

            private boolean nextElementIsAlreadyQueued() {
                return queued != null;
            }

            private boolean queuedAnotherElement() {
                while (asIterator.hasNext()) {
                    A next = asIterator.next();
                    if (predicate.apply(next)) {
                        queued = next;
                        return true;
                    }
                }
                return false;
            }

            private A dequeueNextElement() {
                A swap = queued;
                queued = null;
                return swap;
            }
        };
    }

    /**
     * (a -> Bool) -> [a] -> [a]
     * @param predicate
     * @param as
     * @param <A>
     * @return
     */
    public static <A> FilteringIterable<A> filter(MonadicFunction<? super A, Boolean> predicate, Iterable<A> as) {
        return new FilteringIterable<A>(predicate, as);
    }
}
