package unibo.algat.graph;

import unibo.algat.util.Pair;

import java.util.*;

/**
 * <p>A wrapper {@link Graph} implementation capable of transmitting changes to
 * interested parties. {@code ObservableGraph} doesn't define its own ways of
 * storing a {@code Graph} data but only wraps already existing {@code Graph}
 * implementations (except, of course, another {@code ObservableGraph}).</p>
 * <p>Instances of this class have {@code synchronized} primitives, making
 * them safe for use in background threads.</p>
 * @param <T>
 */
public final class ObservableGraph<T> implements Graph<T> {
	private Graph<T> mGraph;

	private List<NodeChangeListener<T>> mNodeListeners;
	private List<EdgeChangeListener<T>> mEdgeListeners;

	public ObservableGraph (Graph<T> graph) {
		if (graph == null)
			throw new NullPointerException("Graph argument is null");
		if (graph instanceof ObservableGraph)
			throw new IllegalArgumentException(
				"graph argument cannot inherit from ObservableGraph"
			);

		mGraph = graph;
		mNodeListeners = new LinkedList<>();
		mEdgeListeners = new LinkedList<>();
	}

	@Override
	public synchronized boolean insertNode(Node<T> node) {
		final NodeChangeEvent<T> e = new NodeChangeEvent<>(this, node, true);

		// ObservableGraph does not abide to the specification by raising the
		// given exceptions -- it waits for its wrapped Graph to do so

		// If the node was actually inserted (and did not already exist
		// inside the graph) we need to notify listeners
		if (mGraph.insertNode(node)) {
			for (NodeChangeListener<T> l: mNodeListeners)
				l.nodeChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean deleteNode(Node<T> node) {
		final Set<Pair<Node<T>, Node<T>>> edges = new HashSet<>();
		final NodeChangeEvent<T> nodeChanged = new NodeChangeEvent<>(
			this, node, false
		);

		if (mGraph.containsNode(node)) {
			for (Node<T> v: mGraph.adjacents(node))
				edges.add(new Pair<>(node, v));

			for (Node<T> u: mGraph.nodes()) {
				if (mGraph.containsEdge(u, node))
					edges.add(new Pair<>(u, node));
			}

			mGraph.deleteNode(node);

			for (NodeChangeListener<T> l : mNodeListeners)
				l.nodeChanged(nodeChanged);

			for (Pair<Node<T>, Node<T>> edge : edges) {
				EdgeChangeEvent<T> edgeChanged = new EdgeChangeEvent<>(
					this, edge.getFirst(), edge.getSecond(), false
				);

				for (EdgeChangeListener<T> l : mEdgeListeners)
					l.edgeChanged(edgeChanged);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized boolean containsNode(Node<T> needle) {
		return mGraph.containsNode(needle);
	}

	@Override
	public synchronized SortedSet<Node<T>> nodes() {
		return mGraph.nodes();
	}

	@Override
	public synchronized SortedSet<Node<T>> adjacents(Node<T> node) {
		return mGraph.adjacents(node);
	}

	@Override
	public synchronized boolean insertEdge(Node<T> a, Node<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, true);

		if (mGraph.insertEdge(a, b)) {
			for (EdgeChangeListener<T> l: mEdgeListeners)
				l.edgeChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean deleteEdge(Node<T> a, Node<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, false);

		if (mGraph.deleteEdge(a, b)) {
			for (EdgeChangeListener<T> l: mEdgeListeners)
				l.edgeChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean containsEdge(Node<T> a, Node<T> b) {
		return mGraph.containsEdge(a, b);
	}

	@Override
	public synchronized void clear() {
		final List<Node<T>> nodesCopy = List.copyOf(mGraph.nodes());

		for (Node<T> node: nodesCopy) {
			deleteNode(node);
		}
	}

	public synchronized void addNodeChangeListener (NodeChangeListener<T> l) {
		mNodeListeners.add(l);
	}

	public synchronized void removeNodeChangeListener (
		NodeChangeListener<T> l
	) {
		mNodeListeners.remove(l);
	}

	public synchronized void addEdgeChangeListener (EdgeChangeListener<T> l) {
		mEdgeListeners.add(l);
	}

	public synchronized void removeEdgeChangeListener (
		EdgeChangeListener<T> l
	) {
		mEdgeListeners.remove(l);
	}

	@Override
	public synchronized String toString () {
		return mGraph.toString();
	}
}
