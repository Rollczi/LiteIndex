package dev.rollczi.liteindex.space;

import dev.rollczi.liteindex.axis.*;
import dev.rollczi.liteindex.space.indexing.IndexingAlgorithmSet;
import dev.rollczi.liteindex.space.range.SpaceRangeProvider;

import java.util.*;

class UniversalSpaceIndex<SPACE, VECTOR> implements SpaceIndex<SPACE, VECTOR> {

    private final SpaceRangeProvider<SPACE, VECTOR> spaceRangeProvider;

    private final IndexingAlgorithmSet<VECTOR> indexingAlgorithm;
    private final AxesSet<VECTOR> axesSet;

    private final BucketWithBuckets rootBucket;
    private final Set<SPACE> allSpaces = new HashSet<>();

    UniversalSpaceIndex(SpaceRangeProvider<SPACE, VECTOR> spaceRangeProvider, AxesSet<VECTOR> axesSet, IndexingAlgorithmSet<VECTOR> indexingAlgorithmSet) {
        this.spaceRangeProvider = spaceRangeProvider;
        this.indexingAlgorithm = indexingAlgorithmSet;
        this.rootBucket = new BucketWithBuckets(AxesState.create(axesSet));
        this.axesSet = axesSet;
    }

    @Override
    public List<SPACE> get(VECTOR vector) {
        return this.rootBucket.getSpaces(vector);
    }

    @Override
    public Optional<SPACE> getFirst(VECTOR vector) {
        return this.get(vector)
            .stream()
            .findFirst();
    }

    @Override
    public void put(SPACE space) {
        this.allSpaces.add(space);
        this.rootBucket.putSpace(space);
    }

    @Override
    public boolean remove(SPACE space) {
        this.allSpaces.remove(space);
        return this.rootBucket.removeSpace(space);
    }

    @Override
    public void removeAll() {
        this.rootBucket.removeAll();
        this.allSpaces.clear();
    }

    @Override
    public boolean contains(SPACE space) {
        return this.allSpaces.contains(space);
    }

    @Override
    public Set<SPACE> getAll() {
        return Collections.unmodifiableSet(this.allSpaces);
    }

    private interface Bucket<SPACE, VECTOR> {

        List<SPACE> getSpaces(VECTOR vector);

        void putSpace(SPACE space);

        boolean removeSpace(SPACE space);

        void removeAll();

    }

    private class BucketWithBuckets implements Bucket<SPACE, VECTOR> {

        private final AxesState<VECTOR> axesState;
        private final Map<Integer, Bucket<SPACE, VECTOR>> buckets = new HashMap<>();

        BucketWithBuckets(AxesState<VECTOR> axesState) {
            this.axesState = axesState;
        }

        @Override
        public List<SPACE> getSpaces(VECTOR vector) {
            int index = indexingAlgorithm.toIndex(this.axesState.getCurrentAxis(), vector);

            Bucket<SPACE, VECTOR> bucket = this.buckets.get(index);

            if (bucket == null) {
                return Collections.emptyList();
            }

            return bucket.getSpaces(vector);
        }

        @Override
        public void putSpace(SPACE space) {
            Axis<VECTOR> currentAxis = this.axesState.getCurrentAxis();

            VECTOR minVector = spaceRangeProvider.getMinRange(space);
            VECTOR maxVector = spaceRangeProvider.getMaxRange(space);

            int indexMin = indexingAlgorithm.toIndex(currentAxis, minVector);
            int indexMax = indexingAlgorithm.toIndex(currentAxis, maxVector);

            for (int index = indexMin; index <= indexMax; index++) {
                this.putSpace(space, index);
            }
        }

        private void putSpace(SPACE space, int index) {
            Optional<AxesState<VECTOR>> next = this.axesState.withNextAxis();
            Bucket<SPACE, VECTOR> bucket = this.buckets.computeIfAbsent(index, integer -> next
                    .<Bucket<SPACE, VECTOR>>map(BucketWithBuckets::new)
                    .orElse(new BucketBottom()));

            bucket.putSpace(space);
        }

        public void removeAll() {
            for (Bucket<SPACE, VECTOR> bucket : this.buckets.values()) {
                bucket.removeAll();
            }

            this.buckets.clear();
        }

        @Override
        public boolean removeSpace(SPACE space) {
            Axis<VECTOR> currentAxis = this.axesState.getCurrentAxis();

            VECTOR minVector = spaceRangeProvider.getMinRange(space);
            VECTOR maxVector = spaceRangeProvider.getMaxRange(space);

            int indexMin = indexingAlgorithm.toIndex(currentAxis, minVector);
            int indexMax = indexingAlgorithm.toIndex(currentAxis, maxVector);

            boolean isRemoved = false;

            for (int index = indexMin; index <= indexMax; index++) {
                isRemoved |= this.removeSpace(space, index);
            }

            return isRemoved;
        }

        private boolean removeSpace(SPACE space, int index) {
            Bucket<SPACE, VECTOR> bucket = this.buckets.get(index);

            if (bucket != null) {
                return bucket.removeSpace(space);
            }

            return false;
        }
    }

    private class BucketBottom implements Bucket<SPACE, VECTOR> {

        private final Set<SPACE> spaces = new HashSet<>();

        @Override
        public List<SPACE> getSpaces(VECTOR vector) {
            List<SPACE> contain = new ArrayList<>();

            for (SPACE space : this.spaces) {
                if (this.isSpaceContains(space, vector)) {
                    contain.add(space);
                }
            }

            return contain;
        }

        private boolean isSpaceContains(SPACE space, VECTOR currentVector) {
            VECTOR minVector = spaceRangeProvider.getMinRange(space);
            VECTOR maxVector = spaceRangeProvider.getMaxRange(space);

            AxesIterator<VECTOR> axesIterator = new AxesIterator<>(axesSet, currentVector);

            while (axesIterator.hasNext()) {
                AxisResultEntry<VECTOR> entry = axesIterator.next();

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
            this.spaces.add(space);
        }

        @Override
        public boolean removeSpace(SPACE space) {
            return this.spaces.remove(space);
        }

        @Override
        public void removeAll() {
            this.spaces.clear();
        }


    }

}
