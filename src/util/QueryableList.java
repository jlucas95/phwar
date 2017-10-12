package util;

import game.Piece;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Jan on 12-10-2017.
 */
public class QueryableList<T> extends ArrayList<T> {

    public QueryableList(Collection<T> list) {
        super(list);
    }

    public QueryableList(){super();}

    public QueryableList<T> where(Predicate<T> predicate){
        QueryableList<T> ts = new QueryableList<>();
        for (T element : this) {
            if(predicate.test(element)){
                ts.add(element);
            }
        }
        return ts;
    }

    public <U> QueryableList<U> select(Function<T, U> func){
        QueryableList<U> objects = new QueryableList<>();
        for (T t : this) {
            objects.add(func.apply(t));
        }
        return objects;
    }

    public T first(){
        return this.get(0);
    }

    public T Single() {
        if(size() != 1){throw new IllegalStateException("Queryable contains more or less than 1 element"); }
        return first();
    }
}
