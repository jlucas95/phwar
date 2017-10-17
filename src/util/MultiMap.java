package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jan on 13-10-2017.
 */
public class MultiMap<K, E>
        extends Queryable<QueryableEntry<K,E>>
        implements IQueryable<QueryableEntry<K,E>> {


    public boolean put(K key, E value){
        ArrayList<E> e;
        try {
           e = get(key);
        }
        catch (IllegalStateException ex){
            e = new ArrayList<E>();
            add(new QueryableEntry<>(key, e));
        }
        return e.add(value);
    }

    public ArrayList<E> get(K key){
       return where(en -> en.key == key).select(en -> en.values).Single();
    }


//    @Override
//    public Iterator<QueryableEntry<K,E>> iterator(){
//        return super.iterator();
//    }



}
