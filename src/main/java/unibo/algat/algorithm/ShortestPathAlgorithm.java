package unibo.algat.algorithm;

import javafx.application.Platform;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Vertex;
import unibo.algat.graph.WeightFunction;

import java.util.function.BiConsumer;

public abstract class ShortestPathAlgorithm extends SerialAlgorithm<Void> {
	protected Graph<Double> mGraph;
	protected WeightFunction<Double> mWeights;
	protected Vertex<Double> mRoot;
	protected BiConsumer<Vertex<Double>, Vertex<Double>> mOnVisitEdge = (u, v) -> {
	};

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

	public void setRoot (Vertex<Double> root) {
		mRoot = root;
		mReady.set(computeReady());
	}

	public Vertex<Double> getRoot () {
		return mRoot;
	}

	/**
	 * <p>Sets the action to be performed while visiting the {@code (u, v)}
	 * edge. Careful attention must be paid if the submitted code is to run
	 * on the UI thread, in which case it may be passed to
	 * {@link Platform#runLater} or {@link SerialAlgorithm#runAndWait}.</p>
	 */
	public void setOnVisitEdgeAction (
		BiConsumer<Vertex<Double>, Vertex<Double>> action
	) {
		mOnVisitEdge = action;
	}

	/**
	 * @return The action to perform while visiting the {@code (u, v)} edge.
	 * It is an empty action (no-op) by default.
	 */
	public BiConsumer<Vertex<Double>, Vertex<Double>>getOnVisitEdgeAction () {
		return mOnVisitEdge;
	}

	/**
	 * @return {@code true} if this algorithm was determined to be ready to
	 * start, {@code false} otherwise.
	 */
	protected boolean computeReady () {
		return mGraph != null && mWeights != null && mRoot != null;
	}
}
