package unibo.algat.graph;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * <p>A vertex class, to be handled by {@link Graph}. Note that {@code Vertex}
 * instances within a graph are identified by their id only (which needs to be
 * specified by the user) and that any associated data is irrelevant in
 * determining equality with another.</p>
 */
public class Vertex<T> implements Comparable<Vertex<T>> {
	private int mId;
	private ObjectProperty<T> mData;

	public Vertex(int id) {
		this(id, null);
	}

	public Vertex(int id, T data) {
		mId = id;
        mData = new SimpleObjectProperty<>(data);
	}

	public int getId() {
		return mId;
	}

	/**
	 * Sets the data associated with this vertex.
	 */
	public void setData(T data) {
		mData.set(data);
	}

	/**
	 * @return The data associated with this vertex, possibly null if none is
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
		Vertex<T> casted;

		if (other instanceof Vertex) {
			casted = (Vertex<T>) other;

			return mId == casted.mId;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("[%d] %s", mId, mData.get());
	}

	@Override
	public int compareTo(Vertex<T> o) {
		return mId - o.mId;
	}
}
