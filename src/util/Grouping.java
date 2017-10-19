package util;

import java.util.Collection;

public class Grouping<Key, Element>
    extends Queryable<Group<Key, Element>>
        implements IGrouping<Key, Element> {

    public Queryable<Key> keys(){
        QueryableList<Key> keys = new QueryableList<>();
        for (Group<Key, Element> groups : this) {
            keys.add(groups.getKey());
        }
        return keys;
    }

    @Override
    public boolean add(Key key, Element element) {
        Group<Key, Element> group = getOrCreateGroup(key);
        return group.add(element);
    }

    @Override
    public boolean addAll(Key key, Collection<? extends Element> elements) {
        Group<Key, Element> group = getOrCreateGroup(key);
        return group.addAll(elements);
    }

    private Group<Key, Element> getOrCreateGroup(Key key) {
        Group<Key, Element> group;
        try{
            group = getGroup(key);
        }
        catch (IllegalStateException e){
            group = new Group<Key, Element>(key);
        }
        return group;
    }

    private Group<Key, Element> getGroup(Key key) {
        return this.where(g-> g.getKey() == key).single();
    }

    public Queryable<Element> elements(){
        QueryableList<Element> elements = new QueryableList<>();
        for (Group<Key, Element> groupElements : this) {
            elements.addAll(groupElements);
        }
        return elements;
    }
}
