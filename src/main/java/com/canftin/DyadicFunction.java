package com.canftin;

public abstract class DyadicFunction<A, B, C> extends MonadicFunction<Tuple2<A, B>, C> {
    @Override
    public final C apply(Tuple2<A, B> args) {
        return apply(args._1, args._2);
    }

    public abstract C apply(A a, B b);
}