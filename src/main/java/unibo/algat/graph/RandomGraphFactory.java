package unibo.algat.graph;

import unibo.algat.util.ValueFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiPredicate;

/**
 * <p>A {@link GraphFactory} subclass, generating {@code Graph} instances
 * randomly with a specified number of vertices and edges.</p>
 */
public abstract class RandomGraphFactory<T> implements GraphFactory<T> {
	protected int mVertices, mEdges;
	private boolean mSelfEdges;
	protected ValueFactory<T> mValueFactory;

	public RandomGraphFactory(int vertices, int edges) {
		this(vertices, edges, null);
	}

	public RandomGraphFactory(int vertices, int edges, ValueFactory<T> factory) {
		this(vertices, edges, factory, false);
	}

	/**
	 * @param vertices Number of total vertices the generated graph will contain
	 * @param edges Number of total edges the generated graph will contain
	 * @param factory The {@link ValueFactory} object responsible for
	 *                   associating data to graph vertices. See
	 *                   {@link #setValueFactory(ValueFactory)}. A value
	 *                   of {@code null} indicates that default values of type
	 *                   {@code T} will be chosen instead.
	 * @param selfEdges {@code true} if edges of the type {@code (x, x)} are
	 *                                 to be allowed, {@code false} otherwise.                                 .
	 */
	public RandomGraphFactory(
		int vertices, int edges, ValueFactory<T> factory, boolean selfEdges
	) {
		if (vertices < 0 || edges < 0) {
			throw new IllegalArgumentException(
				"Either the number of vertices or edges was negative"
			);
		}
		if (vertices * (vertices + 1) / 2 < edges) {
			throw new IllegalArgumentException(
                "There aren't enough vertices for the requested number of edges"
			);
		}

		mVertices = vertices;
		mEdges = edges;
		mSelfEdges = selfEdges;
		mValueFactory = factory;
	}

	/**
	 * <p>Generates a pseudo-randomly populated graph. The vertices will be
	 * assigned ids in the {@code [0, vertices)} range linearly (no
	 * randomization), while their edges will be picked randomly.</p>
	 * <p>If a {@link ValueFactory} was previously set, it will be used to
	 * associate extra data to the graph vertices.</p>
	 * @return A randomly-generated {@link Graph} instance.
	 */
	@Override
	public final Graph<T> make() {
		final Graph<T> instance = getInstance();
        final ArrayList<Vertex<T>> vertices = new ArrayList<>(mVertices);
        final Random r = new Random(System.currentTimeMillis());
        // Regulates the insertion of new edges -- are they already present,
		// is it an (x, x) type of edge?
        final BiPredicate<Vertex<T>, Vertex<T>> mustDiscardEdge = (u, v) ->
			instance.containsEdge(u, v) || (!mSelfEdges && u.equals(v));

        for (int i = 0; i < mVertices; i++) {
            vertices.add(new Vertex<>(i));

            if (mValueFactory != null)
            	vertices.get(i).setData(mValueFactory.makeValue());

			instance.insertVertex(vertices.get(i));
		}

        for (int i = 0; i < mEdges; i++) {
        	Vertex<T> u, v;

        	// Generate an edge that isn't already present in the graph, thus
			// ensuring that their requested number is respected
            do {
				u = vertices.get(r.nextInt(mVertices));
				v = vertices.get(r.nextInt(mVertices));
			} while (mustDiscardEdge.test(u, v));

            instance.insertEdge(u, v);
		}

        return instance;
	}

	/**
	 * @param factory The {@link ValueFactory} instance generating values (of
	 *                  type {@code T}) associated to the randomly-generated
	 *                  vertices. {@code null} is used to indicate that no
	 *                  arbitrary generation is to be performed, and that
	 *                  default values will be preferred instead.
	 *                  Note that vertices ids will <b>not</b> be generated
	 *                  randomly, as there is no need to do so.
	 */
	public void setValueFactory(ValueFactory<T> factory) {
		mValueFactory = factory;
	}

	/**
	 * @return The {@link ValueFactory} instance responsible for generating
	 * values to random vertices, or {@code null} meaning that no such
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
