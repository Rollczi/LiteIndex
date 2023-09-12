package dev.rollczi.liteindex.space;

import java.util.List;
import java.util.Optional;
import java.util.Set;

class UniversalConcurrentSpaceIndex<SPACE, VECTOR> implements SpaceIndex<SPACE, VECTOR> {

    private final Object lock = new Object();

    private final SpaceIndex<SPACE, VECTOR> index;

    UniversalConcurrentSpaceIndex(SpaceIndex<SPACE, VECTOR> index) {
        this.index = index;
    }

    @Override
    public List<SPACE> get(VECTOR vector) {
        synchronized (lock) {
            return index.get(vector);
        }
    }

    @Override
    public synchronized Optional<SPACE> getFirst(VECTOR vector) {
        synchronized (lock) {
            return index.getFirst(vector);
        }
    }

    @Override
    public synchronized void put(SPACE space) {
        synchronized (lock) {
            index.put(space);
        }
    }

    @Override
    public synchronized boolean remove(SPACE space) {
        synchronized (lock) {
            return index.remove(space);
        }
    }

    @Override
    public synchronized void removeAll() {
        synchronized (lock) {
            index.removeAll();
        }
    }

    @Override
    public synchronized boolean contains(SPACE space) {
        synchronized (lock) {
            return index.contains(space);
        }
    }

    @Override
    public synchronized Set<SPACE> getAll() {
        synchronized (lock) {
            return index.getAll();
        }
    }

}
