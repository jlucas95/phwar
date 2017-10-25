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

    public <K> IGrouping<K, T>  groupBy(Function<T, K> selector){
        Grouping<K, T> groups = new Grouping<>();
        for (T t : this) {
            groups.add(selector.apply(t), t);
            groups.addAll(selector.apply(t), this);
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

    /**
     * Returns the single element in this Queryable
     * @throws IllegalStateException if the Queryable contains less or more than 1 element.
     * @return The lone element of this Queryable
     */
    public T single() {
        if(size() != 1){throw new IllegalStateException("Queryable contains more or less than 1 element"); }
        return first();
    }

    public boolean any(){
        return size() != 0;
    }

    public int sumInt(Function<T, Integer> selector){
        int sum = 0;
        for(T t : this){
            sum += selector.apply(t);
        }
        return sum;
    }

    public double sumDouble(Function<T, Double> selector){
        double sum = 0;
        for(T t : this){
            sum += selector.apply(t);
        }
        return sum;
    }

    public Queryable<T> distinct(){
        Queryable<T> distinct = new QueryableList<>();
        for (T t : this) {
            if(!distinct.contains(t)){
                distinct.add(t);
            }
        }
        return distinct;
    }

    public double average(Function<T, Double> selector){
        double sum = this.sumDouble(selector);
        return sum/size();
    }

    public boolean all(Function<T, Boolean> predicate){
        for (T t : this) {
            if(!predicate.apply(t)) return false;
        }
        return true;
    }


}
