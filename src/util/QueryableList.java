package util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Jan on 12-10-2017.
 */
public class QueryableList<T> extends Queryable<T> implements IQueryable<T> {

    public QueryableList(Collection<T> list) {
        super(list);
    }

    public QueryableList(){super();}


}
