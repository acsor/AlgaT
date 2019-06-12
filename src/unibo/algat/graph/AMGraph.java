package unibo.algat.graph;


import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class AMGraph<T> implements Graph<T> {
	private Node<T>[] mNodes;
	private short[][] mEdges;

	public AMGraph (int capacity) {
		if (capacity > 0) {
			mNodes = (Node<T>[]) new Object[capacity];
			mEdges = new short[capacity][capacity];
		} else {
			throw new IllegalArgumentException("capacity needs to be positive");
		}
	}

	@Override
	public void insertNode(Node<T> node) {
		int id;

		if (node != null) {
			id = node.getId();

			if (0 <= id && id < mEdges.length) {
                // If not already present
				if (mEdges[id] == null)
					mEdges[id] = new short[mEdges.length];
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

            if (0 <= id && id < mEdges.length) {
            	if (mEdges[id] != null) {
					// Deletes associated edges going out from the deleted
					// node -- plausibly more useful for avoiding memory leaks
					// and circular references
					for (int col = 0; col < mEdges.length; col++) {
						if (mEdges[id][col] != null)
							mEdges[id][col] = null;
					}

					mEdges[id] = null;

					// Deletes associated edges going into the deleted node
					for (int row = 0; row < mEdges.length; row++) {
						if (mEdges[row] != null)
							mEdges[row][id] = null;
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

        for (int row = 0; row < mEdges.length; row++) {
            if (mEdges[row][row] != null) {
                b.append(
                	String.format("[%d] %s -> [", row, mEdges[row][row])
				);

                for (int col = 0; col < mEdges[row].length; col++) {
                	// TODO Remove the last trailing comma
                	if (col != row && mEdges[row][col] != null) {
                		b.append(mEdges[row][col]).append(", ");
					}
				}

                b.append("]\n");
			}
		}

        return b.toString();
	}

}
