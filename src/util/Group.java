package util;

import java.util.ArrayList;
import java.util.Collection;

public class Group<K, E> extends ArrayList<E> implements IGroup<K, E> {
    private K key;


    public Group(int initialCapacity, K key) {
        super(initialCapacity);
        this.key = key;
    }

    public Group(K key) {
        this.key = key;
    }

    public Group(Collection<? extends E> c, K key) {
        super(c);
        this.key = key;
    }

    public K getKey() {

        return key;
    }
}
