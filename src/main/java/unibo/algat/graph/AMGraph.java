package unibo.algat.graph;


import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An adjacency-matrix implementation of a {@link Graph}.
 */
public class AMGraph<T> implements Graph<T> {
	private Vertex<T>[] mVertices;
	private short[][] mEdges;

	public AMGraph (int capacity) {
		if (capacity > 0) {
			mVertices = (Vertex<T>[]) new Vertex[capacity];
			mEdges = new short[capacity][capacity];

			for (int i = 0; i < capacity; i++)
				mEdges[i] = new short[capacity];
		} else {
			throw new IllegalArgumentException("capacity needs to be positive");
		}
	}

	@Override
	public boolean insertVertex(Vertex<T> vertex) {
		if (vertex != null) {
			final int id = vertex.getId();

			if (0 <= id && id < mVertices.length) {
				Vertex<T> old = mVertices[id];
				mVertices[id] = vertex;

				return old == null;
			} else {
				throw new IllegalArgumentException(
                    "The given vertex had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("vertex was null");
		}
	}

	@Override
	public boolean deleteVertex(Vertex<T> vertex) {
		if (vertex != null) {
            int id = vertex.getId();

            if (0 <= id && id < mVertices.length) {
            	final Vertex<T> old = mVertices[id];

				mVertices[id] = null;
				mEdges[id] = new short[mVertices.length];

				// Clear the deleted vertex from edges to which it was the end
				for (int row = 0; row < mVertices.length; row++)
					mEdges[row][id] = 0;

				return old != null;
			} else {
				throw new IllegalArgumentException(
					"The given vertex had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("vertex was null");
		}
	}

	@Override
	public boolean containsVertex(Vertex<T> needle) {
		if (needle != null) {
			final int id = needle.getId();

			if (0 <= id && id < mVertices.length) {
				return mVertices[id] != null;
			} else {
				throw new IllegalArgumentException(
					"The given vertex had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("needle was null");
		}
	}

	@Override
	public SortedSet<Vertex<T>> vertices() {
		final TreeSet<Vertex<T>> out = new TreeSet<>();

		for (Vertex<T> v: mVertices) {
			if (v != null)
				out.add(v);
		}

		return out;
	}

	@Override
	public SortedSet<Vertex<T>> adjacents(Vertex<T> vertex) {
		final TreeSet<Vertex<T>> a = new TreeSet<>();

		if (vertex != null) {
			final int id = vertex.getId();

            if (containsVertex(vertex)) {
				for (int i = 0; i < mVertices.length; i++) {
					if (mEdges[id][i] == 1)
						a.add(mVertices[i]);
				}
			} else {
				throw new NoSuchElementException("vertex is not in this graph");
			}
		} else {
			throw new NullPointerException("vertex was null");
		}

		return a;
	}

	@Override
	public boolean insertEdge(Vertex<T> a, Vertex<T> b) {
		if (a != null && b != null) {
			if (containsVertex(a) && containsVertex(b)) {
				final int old = mEdges[a.getId()][b.getId()];

				mEdges[a.getId()][b.getId()] = 1;

				return old == 0;
			} else {
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
			}
		} else {
			throw new NullPointerException("Either a or b was null");
		}
	}

	@Override
	public boolean deleteEdge(Vertex<T> a, Vertex<T> b) {
		if (a != null && b != null) {
			if (containsVertex(a) && containsVertex(b)) {
				final int old = mEdges[a.getId()][b.getId()];

				mEdges[a.getId()][b.getId()] = 0;

				return old == 1;
			} else {
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
			}
		} else {
			throw new NullPointerException("Either a or b was null");
		}
	}

	@Override
	public boolean containsEdge(Vertex<T> a, Vertex<T> b) {
		if (a != null && b != null) {
			if (containsVertex(a) && containsVertex(b))
				return mEdges[a.getId()][b.getId()] == 1;
			else
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
		} else {
			throw new NullPointerException("Either a or b was null");
		}
	}

	@Override
	public void clear() {
		for (int row = 0; row < mVertices.length; row++) {
			mVertices[row] = null;
			mEdges[row] = new short[mVertices.length];
		}
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

		sb.append(getClass().getName()).append("\n");

		for (int row = 0; row < mVertices.length; row++) {
			sb.append(mVertices[row]).append(" -> [");

			for (int col = 0; col < mVertices.length; col++) {
				if (mEdges[row][col] == 1)
					sb.append(mVertices[col]);
			}

			sb.append("]\n");
		}

        return sb.toString();
	}
}
