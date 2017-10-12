package util;

/**
 * Created by Jan on 12-10-2017.
 */
public class Tuple<T, U> {
    private final T T;
    private final U U;

    public Tuple(T t, U t1) {
        T = t;
        U = t1;
    }


    public T getFirst() {
        return T;
    }

    public U getSecond() {
        return U;
    }
}
