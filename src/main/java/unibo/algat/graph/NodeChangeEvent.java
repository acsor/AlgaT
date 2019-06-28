package unibo.algat.graph;

import java.util.EventObject;

public class NodeChangeEvent<T> extends EventObject {
	private Node<T> mNode;
	private boolean mInserted;
	/**
	 * <p>Constructs a {@code NodeChangEvent}.</p>
	 *
	 * @param source the object on which the Event initially occurred
	 * @param node the {@link Node} instance subject of this change
	 * @param inserted {@code true} if the subject node was inserted, {@code
	 * false} otherwise
	 * @throws IllegalArgumentException if source is null
	 */
	public NodeChangeEvent(Object source, Node<T> node, boolean inserted) {
		super(source);

		mNode = node;
		mInserted = inserted;
	}

	public Node<T> getNode () {
		return mNode;
	}

	public boolean wasInserted () {
		return mInserted;
	}

	public boolean wasDeleted () {
		return !mInserted;
	}
}
