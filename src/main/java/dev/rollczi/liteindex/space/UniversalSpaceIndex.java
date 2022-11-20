package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.axis.AxesSet;
import dev.rollczi.liteindex.axis.AxesState;
import dev.rollczi.liteindex.axis.Axis;
import dev.rollczi.liteindex.axis.AxisResultEntry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class UniversalSpaceIndex<SPACE, VECTOR> implements SpaceIndex<SPACE, VECTOR> {

    private final SpaceVectorProvider<SPACE, VECTOR> spaceVectorProvider;

    private final IndexSize indexSize;
    private final BucketWithBuckets rootBucket;
    private final AxesSet<VECTOR> axesSet;

    private final Set<SPACE> allSpaces = new HashSet<>();
    private final Map<SPACE, Set<BucketBottom>> bucketsBySpace = new HashMap<>();

    UniversalSpaceIndex(SpaceVectorProvider<SPACE, VECTOR> spaceVectorProvider, AxesSet<VECTOR> axesSet, IndexSize indexSize) {
        this.spaceVectorProvider = spaceVectorProvider;
        this.indexSize = indexSize;
        this.rootBucket = new BucketWithBuckets(AxesState.create(axesSet));
        this.axesSet = axesSet;
    }

    @Override
    public Set<SPACE> get(VECTOR vector) {
        return rootBucket.getSpaces(vector);
    }

    @Override
    public Optional<SPACE> getFirst(VECTOR vector) {
        return this.get(vector).stream().findFirst();
    }

    @Override
    public void put(SPACE space) {
        rootBucket.putSpace(space);
        allSpaces.add(space);
    }

    @Override
    public synchronized boolean remove(SPACE space) {
        boolean isRemoved = false;
        Set<BucketBottom> removed = bucketsBySpace.remove(space);

        if (removed != null) {
            for (BucketBottom bucket : removed) {
                bucket.nativeRemoveSpace(space);
            }

            isRemoved = true;
        }

        isRemoved |= allSpaces.remove(space);

        return isRemoved;
    }

    @Override
    public boolean contains(SPACE space) {
        return allSpaces.contains(space);
    }

    @Override
    public Collection<SPACE> getAll() {
        return Collections.unmodifiableSet(allSpaces);
    }

    private interface Bucket<SPACE, VECTOR> {

        Set<SPACE> getSpaces(VECTOR vector);

        void putSpace(SPACE space);

    }

    private class BucketWithBuckets implements Bucket<SPACE, VECTOR> {

        private final AxesState<VECTOR> axesState;
        private final Map<Integer, Bucket<SPACE, VECTOR>> buckets = new HashMap<>();

        BucketWithBuckets(AxesState<VECTOR> axesState) {
            this.axesState = axesState;
        }

        @Override
        public Set<SPACE> getSpaces(VECTOR vector) {
            double coordinate = axesState.getCurrentAxis().getAxisCoordinate(vector);
            int index = indexSize.toIndex(coordinate);

            Bucket<SPACE, VECTOR> bucket = buckets.get(index);

            if (bucket == null) {
                return Collections.emptySet();
            }

            return bucket.getSpaces(vector);
        }

        @Override
        public void putSpace(SPACE space) {
            VECTOR minVector = spaceVectorProvider.getMinVector(space);
            VECTOR maxVector = spaceVectorProvider.getMaxVector(space);

            Axis<VECTOR> currentAxis = axesState.getCurrentAxis();

            double minCoordinate = currentAxis.getAxisCoordinate(minVector);
            double maxCoordinate = currentAxis.getAxisCoordinate(maxVector);

            int indexMin = indexSize.toIndex(minCoordinate);
            int indexMax = indexSize.toIndex(maxCoordinate);

            for (int index = indexMin; index <= indexMax; index++) {
                this.putSpace(space, index);
            }
        }

        private void putSpace(SPACE space, int index) {
            Optional<AxesState<VECTOR>> next = axesState.withNextAxis();
            Bucket<SPACE, VECTOR> bucket = buckets.computeIfAbsent(index, integer -> next
                    .<Bucket<SPACE, VECTOR>>map(BucketWithBuckets::new)
                    .orElse(new BucketBottom()));

            bucket.putSpace(space);
        }

    }

    private class BucketBottom implements Bucket<SPACE, VECTOR> {

        private final Set<SPACE> spaces = new HashSet<>();

        @Override
        public Set<SPACE> getSpaces(VECTOR vector) {
            Set<SPACE> contain = new HashSet<>();

            for (SPACE space : spaces) {
                if (this.isSpaceContains(space, vector)) {
                    contain.add(space);
                }
            }

            return contain;
        }

        private boolean isSpaceContains(SPACE space, VECTOR currentVector) {
            VECTOR minVector = spaceVectorProvider.getMinVector(space);
            VECTOR maxVector = spaceVectorProvider.getMaxVector(space);

            for (AxisResultEntry<VECTOR> entry : axesSet.createResult(currentVector)) {
                double currentCoordinate = entry.getCoordinate();
                Axis<VECTOR> axis = entry.getAxis();

                if (axis.getAxisCoordinate(minVector) > currentCoordinate) {
                    return false;
                }

                if (axis.getAxisCoordinate(maxVector) < currentCoordinate) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void putSpace(SPACE space) {
            spaces.add(space);
            bucketsBySpace.computeIfAbsent(space, key -> new HashSet<>()).add(this);
        }

        private void nativeRemoveSpace(SPACE space) {
            spaces.remove(space);
        }

    }

}
