package dev.rollczi.liteindex.space;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SpaceIndex<SPACE, VECTOR> {

    Set<SPACE> get(VECTOR vector);

    Optional<SPACE> getFirst(VECTOR vector);

    void put(SPACE space);

    boolean remove(SPACE space);

    boolean contains(SPACE space);

    Collection<SPACE> getAll();

    static <SPACE, VECTOR> SpaceIndexBuilder<SPACE, VECTOR> builder() {
        return new SpaceIndexBuilder<>();
    }

}
