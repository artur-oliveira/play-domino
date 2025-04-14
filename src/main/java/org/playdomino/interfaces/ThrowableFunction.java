package org.playdomino.interfaces;

@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R apply(T t) throws Exception;

}