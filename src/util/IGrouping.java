package util;

import java.util.Collection;
import java.util.Map;

public interface IGrouping<Key, Element> extends IQueryable<Group<Key, Element>>{

    boolean add(Key apply, Element t);

    boolean addAll(Key apply, Collection<? extends Element> ts);
}
