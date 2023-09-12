package dev.rollczi.liteindex.space.indexing;

import dev.rollczi.liteindex.axis.Axis;

import java.util.HashMap;
import java.util.Map;

public class IndexingAlgorithmSet<VECTOR> {

    private final Map<Axis<VECTOR>, IndexingAlgorithm<VECTOR>> algorithms = new HashMap<>();
    private final IndexingAlgorithm<VECTOR> defaultAlgorithm;

    private IndexingAlgorithmSet(IndexingAlgorithm<VECTOR> defaultAlgorithm) {
        this.defaultAlgorithm = defaultAlgorithm;
    }

    public int toIndex(Axis<VECTOR> axis, VECTOR vector) {
        IndexingAlgorithm<VECTOR> algorithm = algorithms.get(axis);

        if (algorithm == null) {
            algorithm = defaultAlgorithm;
        }

        return algorithm.toIndex(axis, vector);
    }

    public static <VECTOR> Builder<VECTOR> builder() {
        return new Builder<>();
    }

    public static class Builder<VECTOR> {

        private final Map<Axis<VECTOR>, IndexingAlgorithm<VECTOR>> algorithms = new HashMap<>();
        private IndexingAlgorithm<VECTOR> defaultAlgorithm;

        public Builder<VECTOR> defaultAlgorithm(IndexingAlgorithm<VECTOR> defaultAlgorithm) {
            this.defaultAlgorithm = defaultAlgorithm;
            return this;
        }

        public Builder<VECTOR> algorithm(Axis<VECTOR> axis, IndexingAlgorithm<VECTOR> algorithm) {
            algorithms.put(axis, algorithm);
            return this;
        }

        public IndexingAlgorithmSet<VECTOR> build() {
            IndexingAlgorithmSet<VECTOR> algorithm = new IndexingAlgorithmSet<>(defaultAlgorithm);
            algorithm.algorithms.putAll(algorithms);
            return algorithm;
        }

    }

}
