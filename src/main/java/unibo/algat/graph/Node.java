package unibo.algat.graph;

/**
 * <p>A node class, to be handled by {@code Graph<T>}. Note that {@code Node}
 * instances within a graph are identified by their id only (which needs to be
 * specified by the user) and that any associated data is irrelevant in
 * determining whether a node is equal to another.</p>
 */
public class Node<T> {
	private int mId;
	private T mData;

	public Node(int id) {
		this(id, null);
	}

	public Node(int id, T data) {
		mId = id;
		mData = data;
	}

	public int getId() {
		return mId;
	}

	/**
	 * Sets the data associated with this node.
	 */
	public void setData(T data) {
		mData = data;
	}

	/**
	 * @return The data associated with this node, possibly null if none is
	 * contained.
	 */
	public T getData() {
		return mData;
	}

	@Override
	public int hashCode() {
		return mId;
	}

	@Override
	public boolean equals(Object other) {
		Node<T> casted;

		if (other instanceof Node) {
			casted = (Node<T>) other;

			return mId == casted.mId;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s", mId, mData);
	}
}
