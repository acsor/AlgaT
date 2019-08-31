package unibo.algat.graph;

import java.util.EventObject;

public class VertexChangeEvent<T> extends EventObject {
	private Vertex<T> mVertex;
	private boolean mInserted;
	/**
	 * <p>Constructs a {@code VertexChangEvent}.</p>
	 *
	 * @param source the object on which the Event initially occurred
	 * @param vertex the {@link Vertex} instance subject of this change
	 * @param inserted {@code true} if the subject vertex was inserted, {@code
	 * false} otherwise
	 * @throws IllegalArgumentException if source is null
	 */
	public VertexChangeEvent(Object source, Vertex<T> vertex, boolean inserted) {
		super(source);

		mVertex = vertex;
		mInserted = inserted;
	}

	public Vertex<T> getVertex() {
		return mVertex;
	}

	public boolean wasInserted () {
		return mInserted;
	}

	public boolean wasDeleted () {
		return !mInserted;
	}
}
