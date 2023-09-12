package dev.rollczi.liteindex.space;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpaceIndex<SPACE, VECTOR> {

    List<SPACE> get(VECTOR vector);

    Optional<SPACE> getFirst(VECTOR vector);

    void put(SPACE space);

    boolean remove(SPACE space);

    void removeAll();

    boolean contains(SPACE space);

    Set<SPACE> getAll();

    static <SPACE, VECTOR> SpaceIndexBuilder<SPACE, VECTOR> builder() {
        return new SpaceIndexBuilder<>();
    }

}
