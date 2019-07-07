package unibo.algat.graph;

import unibo.algat.util.ValueFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * <p>A {@link GraphFactory} subclass, generating {@code Graph} instances
 * randomly with a specified number of nodes and edges.</p>
 */
public abstract class RandomGraphFactory<T> implements GraphFactory<T> {
	protected int mNodes, mEdges;
	protected ValueFactory<T> mValueFactory;

	public RandomGraphFactory(int nodes, int edges) {
		this(nodes, edges, null);
	}

	/**
	 * @param nodes Number of total nodes the generated graph will contain
	 * @param edges Number of total edges the generated graph will contain
	 * @param factory The {@link ValueFactory} object responsible for
	 *                   associating data to graph nodes. See
	 *                   {@link #setValueFactory(ValueFactory)}. A value
	 *                   of {@code null} indicates that default values of type
	 *                   {@code T} will be chosen instead.
	 */
	public RandomGraphFactory(int nodes, int edges, ValueFactory<T> factory) {
		if (nodes < 0 || edges < 0) {
			throw new IllegalArgumentException(
				"Either the number of nodes or edges was negative"
			);
		}
		if (Math.pow(nodes, 2) < edges) {
			throw new IllegalArgumentException(
                "There aren't enough nodes to makeValue the number of required" +
					" edges"
			);
		}

		mNodes = nodes;
		mEdges = edges;
		mValueFactory = factory;
	}

	/**
	 * <p>Generates a pseudo-randomly populated graph. The nodes will be
	 * assigned ids in the {@code [0, nodes)} range linearly (no
	 * randomization), while their edges will be picked randomly.</p>
	 * <p>If a {@link ValueFactory} was previously set, it will be used to
	 * associated extra data to the graph nodes.</p>
	 * @return A randomly-makeValue {@link Graph} instance.
	 */
	@Override
	public final Graph<T> makeGraph() {
		final Graph<T> instance = getInstance();
        final ArrayList<Node<T>> nodes = new ArrayList<>(mNodes);
        final Random r = new Random(System.currentTimeMillis());

        for (int i = 0; i < mNodes; i++) {
            nodes.add(new Node<>(i));

            if (mValueFactory != null)
            	nodes.get(i).setData(mValueFactory.makeValue());

			instance.insertNode(nodes.get(i));
		}

        for (int i = 0; i < mEdges; i++) {
        	Node<T> u, v;

        	// Generate an edge that isn't already present in the graph, thus
			// ensuring that their requested number is respected
            do {
				u = nodes.get(r.nextInt(mNodes));
				v = nodes.get(r.nextInt(mNodes));
			} while (instance.containsEdge(u, v));

            instance.insertEdge(u, v);
		}

        return instance;
	}

	/**
	 * @param factory The {@link ValueFactory} instance generating values (of
	 *                  type {@code T}) associated to the randomly-generated
	 *                  nodes. {@code null} is used to indicate that no
	 *                  arbitrary generation is to be performed, and that
	 *                  default values will be preferred instead.
	 *                  Note that nodes ids will <b>not</b> be generated
	 *                  randomly, as there is no need to do so.
	 */
	public void setValueFactory(ValueFactory<T> factory) {
		mValueFactory = factory;
	}

	/**
	 * @return The {@link ValueFactory} instance responsible for generating
	 * values to random nodes, or {@code null} meaning that no such
	 * factory exists and that default values will be picked instead.
	 */
	public ValueFactory<T> getValueFactory() {
		return mValueFactory;
	}

	/**
	 * Factory method pattern, delegating the construction of a concrete
	 * {@link Graph} instance to {@code RandomGraphFactory} subclasses.
	 * @return A concrete, empty {@link Graph} instance to populate later.
	 */
	protected abstract Graph<T> getInstance ();
}
