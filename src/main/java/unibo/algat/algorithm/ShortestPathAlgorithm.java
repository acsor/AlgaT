package unibo.algat.algorithm;

import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;
import unibo.algat.graph.WeightFunction;

public abstract class ShortestPathAlgorithm extends SerialAlgorithm<Void> {
	protected Graph<Double> mGraph;
	protected WeightFunction<Double> mWeights;
	protected Node<Double> mRoot;

	public void setGraph (Graph<Double> graph) {
		mGraph = graph;
		mReady.set(computeReady());
	}

	public Graph<Double> getGraph () {
		return mGraph;
	}

	public void setWeightFunction (WeightFunction<Double> func) {
		mWeights = func;
		mReady.set(computeReady());
	}

	public WeightFunction<Double> getWeightFunction () {
		return mWeights;
	}

	public void setRoot (Node<Double> root) {
		mRoot = root;
		mReady.set(computeReady());
	}

	public Node<Double> getRoot () {
		return mRoot;
	}

	/**
	 * @return {@code true} if this algorithm was determined to be ready to
	 * start, {@code false} otherwise.
	 */
	protected boolean computeReady () {
		return mGraph != null && mWeights != null && mRoot != null;
	}
}
