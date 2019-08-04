package unibo.algat.graph;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * <p>A node class, to be handled by {@link Graph}. Note that {@code Node}
 * instances within a graph are identified by their id only (which needs to be
 * specified by the user) and that any associated data is irrelevant in
 * determining equality with another.</p>
 */
public class Node<T> implements Comparable<Node<T>> {
	private int mId;
	private ObjectProperty<T> mData;

	public Node(int id) {
		this(id, null);
	}

	public Node(int id, T data) {
		mId = id;
        mData = new SimpleObjectProperty<>(data);
	}

	public int getId() {
		return mId;
	}

	/**
	 * Sets the data associated with this node.
	 */
	public void setData(T data) {
		mData.set(data);
	}

	/**
	 * @return The data associated with this node, possibly null if none is
	 * contained.
	 */
	public T getData() {
		return mData.get();
	}

	public ObjectProperty<T> dataProperty () {
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
		return String.format("[%d] %s", mId, mData.get());
	}

	@Override
	public int compareTo(Node<T> o) {
		return mId - o.mId;
	}
}
