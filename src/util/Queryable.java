package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Jan on 13-10-2017.
 */
abstract public class Queryable<T> extends ArrayList<T> {

    Queryable(Collection<? extends T> c) {
        super(c);
    }

    Queryable(){
        super();
    }

    public <K> MultiMap<K,T>  groupBy(Function<T, K> selector){
        MultiMap<K, T> groups = new MultiMap<>();
        for (T t : this) {
            groups.put(selector.apply(t), t);
        }
        return groups;
    }

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

    public boolean any(){
        return size() != 0;
    }

}
