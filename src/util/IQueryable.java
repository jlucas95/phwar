package util;

import java.util.Enumeration;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Jan on 13-10-2017.
 */
public interface IQueryable<T> extends Iterable<T> {
    public QueryableList<T> where(Predicate<T> predicate);

    public <U> QueryableList<U> select(Function<T, U> func);

    public T first();

    public T Single() ;

    public boolean any();
}
