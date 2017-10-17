package util;

import java.util.ArrayList;

/**
 * Created by Jan on 13-10-2017.
 */
public class QueryableEntry<U,V> {
    U key;
    ArrayList<V> values;

    public U getKey() {
        return key;
    }

    public ArrayList<V> getValues() {
        return values;
    }

    public QueryableEntry(U key, ArrayList<V> value) {
        this.key = key;
        this.values = value;
    }

}
