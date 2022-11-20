package dev.rollczi.liteindex.space;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

class UniversalConcurrentSpaceIndex<SPACE, VECTOR> implements SpaceIndex<SPACE, VECTOR> {

    private final SpaceIndex<SPACE, VECTOR> index;

    UniversalConcurrentSpaceIndex(SpaceIndex<SPACE, VECTOR> index) {
        this.index = index;
    }

    @Override
    public synchronized Set<SPACE> get(VECTOR vector) {
        return index.get(vector);
    }

    @Override
    public synchronized Optional<SPACE> getFirst(VECTOR vector) {
        return index.getFirst(vector);
    }

    @Override
    public synchronized void put(SPACE space) {
        index.put(space);
    }

    @Override
    public synchronized boolean remove(SPACE space) {
        return index.remove(space);
    }

    @Override
    public synchronized boolean contains(SPACE space) {
        return index.contains(space);
    }

    @Override
    public synchronized Collection<SPACE> getAll() {
        return index.getAll();
    }

}
