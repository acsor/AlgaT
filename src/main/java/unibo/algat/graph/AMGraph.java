package unibo.algat.graph;


import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

public class AMGraph<T> implements Graph<T> {
	private Node<T>[] mNodes;
	private short[][] mEdges;

	public AMGraph (int capacity) {
		if (capacity > 0) {
			mNodes = (Node<T>[]) new Node[capacity];
			mEdges = new short[capacity][capacity];

			for (int i = 0; i < capacity; i++)
				mEdges[i] = new short[capacity];
		} else {
			throw new IllegalArgumentException("capacity needs to be positive");
		}
	}

	@Override
	public boolean insertNode(Node<T> node) {
		if (node != null) {
			final int id = node.getId();

			if (0 <= id && id < mNodes.length) {
				Node<T> old = mNodes[id];
				mNodes[id] = node;

				return old == null;
			} else {
				throw new IllegalArgumentException(
                    "The given node had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("node was null");
		}
	}

	@Override
	public boolean deleteNode(Node<T> node) {
		if (node != null) {
            int id = node.getId();

            if (0 <= id && id < mNodes.length) {
            	final Node<T> old = mNodes[id];

				mNodes[id] = null;
				mEdges[id] = new short[mNodes.length];

				// Clear the deleted node from edges to which it was the end
				for (int row = 0; row < mNodes.length; row++)
					mEdges[row][id] = 0;

				return old != null;
			} else {
				throw new IllegalArgumentException(
					"The given node had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("node was null");
		}
	}

	@Override
	public boolean containsNode(Node<T> needle) {
		if (needle != null) {
			final int id = needle.getId();

			if (0 <= id && id < mNodes.length) {
				return mNodes[id] != null;
			} else {
				throw new IllegalArgumentException(
					"The given node had an inappropriate id: " + id
				);
			}
		} else {
			throw new NullPointerException("needle was null");
		}
	}

	@Override
	public SortedSet<Node<T>> nodes() {
		final TreeSet<Node<T>> out = new TreeSet<>();

		for (Node<T> v: mNodes) {
			if (v != null)
				out.add(v);
		}

		return out;
	}

	@Override
	public SortedSet<Node<T>> adjacents(Node<T> node) {
		final TreeSet<Node<T>> a = new TreeSet<>();

		if (node != null) {
			final int id = node.getId();

            if (containsNode(node)) {
				for (int i = 0; i < mNodes.length; i++) {
					if (mEdges[id][i] == 1)
						a.add(mNodes[i]);
				}
			} else {
				throw new NoSuchElementException("node is not in this graph");
			}
		} else {
			throw new NullPointerException("node was null");
		}

		return a;
	}

	@Override
	public boolean insertEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b)) {
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
	public boolean deleteEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b)) {
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
	public boolean containsEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b))
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
    public String toString() {
        final StringBuilder sb = new StringBuilder();

		sb.append(getClass().getName()).append("\n");

		for (int row = 0; row < mNodes.length; row++) {
			sb.append(mNodes[row]).append(" -> [");

			for (int col = 0; col < mNodes.length; col++) {
				if (mEdges[row][col] == 1)
					sb.append(mNodes[col]);
			}

			sb.append("]\n");
		}

        return sb.toString();
	}
}
