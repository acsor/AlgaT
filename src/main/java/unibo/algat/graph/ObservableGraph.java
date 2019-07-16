package unibo.algat.graph;

import unibo.algat.util.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// TODO Rewrite the ObservableGraph system by relying on the java.beans package
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
	public void insertNode(Node<T> node) {
		final NodeChangeEvent<T> e = new NodeChangeEvent<>(this, node, true);

		// ObservableGraph does not abide to the specification by raising the
		// given exceptions -- it waits for its wrapped Graph to do so
		mGraph.insertNode(node);

		for (NodeChangeListener<T> l: mNodeListeners)
			l.nodeChanged(e);
	}

	@Override
	public void deleteNode(Node<T> node) {
		// TODO Test
		final Set<Pair<Node<T>, Node<T>>> edges = new HashSet<>();
		final NodeChangeEvent<T> nodeChanged = new NodeChangeEvent<>(
			this, node, false
		);

		for (Node<T> v: mGraph.adjacents(node))
			edges.add(new Pair<>(node, v));

		for (Node<T> u: mGraph.nodes()) {
			if (mGraph.containsEdge(u, node))
				edges.add(new Pair<>(u, node));
		}

		mGraph.deleteNode(node);

		for (NodeChangeListener<T> l: mNodeListeners)
			l.nodeChanged(nodeChanged);

		for (Pair<Node<T>, Node<T>> edge: edges) {
			EdgeChangeEvent<T> edgeChanged = new EdgeChangeEvent<>(
				this, edge.getFirst(), edge.getSecond(), false
			);

            for (EdgeChangeListener<T> l: mEdgeListeners)
            	l.edgeChanged(edgeChanged);
		}
	}

	@Override
	public boolean containsNode(Node<T> needle) {
		return mGraph.containsNode(needle);
	}

	@Override
	public Set<Node<T>> nodes() {
		return mGraph.nodes();
	}

	@Override
	public Set<Node<T>> adjacents(Node<T> node) {
		return mGraph.adjacents(node);
	}

	@Override
	public void insertEdge(Node<T> a, Node<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, true);

		mGraph.insertEdge(a, b);

        for (EdgeChangeListener<T> l: mEdgeListeners)
        	l.edgeChanged(e);
	}

	@Override
	public void deleteEdge(Node<T> a, Node<T> b) {
		final EdgeChangeEvent<T> e = new EdgeChangeEvent<>(this, a, b, false);

		mGraph.deleteEdge(a, b);

		for (EdgeChangeListener<T> l: mEdgeListeners)
			l.edgeChanged(e);
	}

	@Override
	public boolean containsEdge(Node<T> a, Node<T> b) {
		return mGraph.containsEdge(a, b);
	}

	public void addNodeChangeListener (NodeChangeListener<T> l) {
		mNodeListeners.add(l);
	}

	public void removeNodeChangeListener (NodeChangeListener<T> l) {
		mNodeListeners.remove(l);
	}

	public void addEdgeChangeListener (EdgeChangeListener<T> l) {
		mEdgeListeners.add(l);
	}

	public void removeEdgeChangeListener (EdgeChangeListener<T> l) {
		mEdgeListeners.remove(l);
	}

	@Override
	public String toString () {
		return mGraph.toString();
	}
}
