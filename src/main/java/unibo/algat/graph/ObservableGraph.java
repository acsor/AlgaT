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

	private List<VertexChangeListener<T>> mVertexListeners;
	private List<EdgeChangeListener<T>> mEdgeListeners;

	public ObservableGraph (Graph<T> graph) {
		if (graph == null)
			throw new NullPointerException("Graph argument is null");
		if (graph instanceof ObservableGraph)
			throw new IllegalArgumentException(
				"graph argument cannot inherit from ObservableGraph"
			);

		mGraph = graph;
		mVertexListeners = new LinkedList<>();
		mEdgeListeners = new LinkedList<>();
	}

	@Override
	public synchronized boolean insertVertex(Vertex<T> vertex) {
		final VertexChangeEvent<T> e = new VertexChangeEvent<>(this, vertex, true);

		// ObservableGraph does not abide to the specification by raising the
		// given exceptions -- it waits for its wrapped Graph to do so

		// If the vertex was actually inserted (and did not already exist
		// inside the graph) we need to notify listeners
		if (mGraph.insertVertex(vertex)) {
			for (VertexChangeListener<T> l: mVertexListeners)
				l.vertexChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean deleteVertex(Vertex<T> vertex) {
		final Set<Pair<Vertex<T>, Vertex<T>>> edges = new HashSet<>();
		final VertexChangeEvent<T> vertexChanged = new VertexChangeEvent<>(
			this, vertex, false
		);

		if (mGraph.containsVertex(vertex)) {
			for (Vertex<T> v: mGraph.adjacents(vertex))
				edges.add(new Pair<>(vertex, v));

			for (Vertex<T> u: mGraph.vertices()) {
				if (mGraph.containsEdge(u, vertex))
					edges.add(new Pair<>(u, vertex));
			}

			mGraph.deleteVertex(vertex);

			for (VertexChangeListener<T> l : mVertexListeners)
				l.vertexChanged(vertexChanged);

			for (Pair<Vertex<T>, Vertex<T>> edge : edges) {
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
	public synchronized boolean containsVertex(Vertex<T> needle) {
		return mGraph.containsVertex(needle);
	}

	@Override
	public synchronized SortedSet<Vertex<T>> vertices() {
		return mGraph.vertices();
	}

	@Override
	public synchronized SortedSet<Vertex<T>> adjacents(Vertex<T> vertex) {
		return mGraph.adjacents(vertex);
	}

	@Override
	public synchronized boolean insertEdge(Vertex<T> a, Vertex<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, true);

		if (mGraph.insertEdge(a, b)) {
			for (EdgeChangeListener<T> l: mEdgeListeners)
				l.edgeChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean deleteEdge(Vertex<T> a, Vertex<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, false);

		if (mGraph.deleteEdge(a, b)) {
			for (EdgeChangeListener<T> l: mEdgeListeners)
				l.edgeChanged(e);

			return true;
		}

		return false;
	}

	@Override
	public synchronized boolean containsEdge(Vertex<T> a, Vertex<T> b) {
		return mGraph.containsEdge(a, b);
	}

	@Override
	public synchronized void clear() {
		final List<Vertex<T>> toDelete = List.copyOf(mGraph.vertices());

		for (Vertex<T> vertex: toDelete) {
			deleteVertex(vertex);
		}
	}

	public synchronized void addVertexChangeListener(VertexChangeListener<T> l) {
		mVertexListeners.add(l);
	}

	public synchronized void removeVertexChangeListener(
		VertexChangeListener<T> l
	) {
		mVertexListeners.remove(l);
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
