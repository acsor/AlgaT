package unibo.algat.graph;


import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class AMGraph<T> implements Graph<T> {
	/**
	 * <p>The adjacency matrix storing data about vertices and edges. The
	 * convention here is that if {@code mEntries[row] == null}, then the
	 * node with id equal to {@code row} does not exist within the graph.</p>
	 */
	private Node<T>[][] mEntries;

	public AMGraph (int capacity) {
		if (capacity > 0) {
			mEntries = (Node<T>[][]) new Object[capacity][capacity];
		} else {
			throw new IllegalArgumentException("capacity needs to be positive");
		}
	}

	@Override
	public void insertNode(Node<T> node) {
		int id;

		if (node != null) {
			id = node.getId();

			if (0 <= id && id < mEntries.length) {
                // If not already present
				if (mEntries[id] == null)
					mEntries[id] = (Node<T>[]) new Object[mEntries.length];
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
	public void deleteNode(Node<T> node) {
		int id;

		if (node != null) {
            id = node.getId();

            if (0 <= id && id < mEntries.length) {
            	if (mEntries[id] != null) {
					// Deletes associated edges going out from the deleted
					// node -- plausibly more useful for avoiding memory leaks
					// and circular references
					for (int col = 0; col < mEntries.length; col++) {
						if (mEntries[id][col] != null)
							mEntries[id][col] = null;
					}

					mEntries[id] = null;

					// Deletes associated edges going into the deleted node
					for (int row = 0; row < mEntries.length; row++) {
						if (mEntries[row] != null)
							mEntries[row][id] = null;
					}
				}
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
			// TODO Implement -- you basically need to check that the node id
			//  is valid and that the associated matrix row is not null.
			//  Nothing more, nothing less
			throw new UnsupportedOperationException("Not implemented");
		} else {
			throw new NullPointerException("needle was null");
		}
	}

	@Override
	public Set<Node<T>> vertices() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Set<Node<T>> adjacents(Node<T> node) {
		int id;
		HashSet<Node<T>> a = new HashSet<>();

		if (node != null) {
			id = node.getId();

            if (containsNode(node)) {
				throw new UnsupportedOperationException("Not implemented");
			} else {
				throw new NoSuchElementException("node is not in this graph");
			}
		} else {
			throw new NullPointerException("node was null");
		}

		return a;
	}

	@Override
	public void insertEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b))
				throw new UnsupportedOperationException("Not implemented");
			else
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
		} else {
			throw new NullPointerException("either a or b are null");
		}
	}

	@Override
	public void deleteEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b))
				throw new UnsupportedOperationException("Not implemented");
			else
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
		} else {
			throw new NullPointerException("either a or b are null");
		}
	}

	@Override
	public boolean containsEdge(Node<T> a, Node<T> b) {
		if (a != null && b != null) {
			if (containsNode(a) && containsNode(b))
				throw new UnsupportedOperationException("Not implemented");
			else
				throw new NoSuchElementException(
					"either a or b are not in graph"
				);
		} else {
			throw new NullPointerException("either a or b were null");
		}
	}

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();

        for (int row = 0; row < mEntries.length; row++) {
            if (mEntries[row][row] != null) {
                b.append(
                	String.format("[%d] %s -> [", row, mEntries[row][row])
				);

                for (int col = 0; col < mEntries[row].length; col++) {
                	// TODO Remove the last trailing comma
                	if (col != row && mEntries[row][col] != null) {
                		b.append(mEntries[row][col]).append(", ");
					}
				}

                b.append("]\n");
			}
		}

        return b.toString();
	}

}
