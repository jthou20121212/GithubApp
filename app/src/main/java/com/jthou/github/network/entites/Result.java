package com.jthou.github.network.entites;

/**
 * @author jthou
 * @version 1.0.0
 * @date 24-05-2020
 */
public class Result<T> {

    private T value;
    private Throwable error;

    public T getValue() {
        return value;
    }

    public Throwable getError() {
        return error;
    }

    public T component1() {
        return value;
    }

    public Throwable component2() {
        return error;
    }

    public static <T>  Result<T> of(T value) {
        Result<T> result = new Result<>();
        result.value = value;
        return result;
    }

    public static <T>  Result<T> of(Throwable error) {
        Result<T> result = new Result<>();
        result.error = error;
        return result;
    }

}
