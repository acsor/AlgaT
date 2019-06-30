package unibo.algat.graph;

import java.util.EventObject;

public class EdgeChangeEvent<T> extends EventObject {
	private Node<T> mU, mV;
	private boolean mInserted;

	public EdgeChangeEvent(
		Object source, Node<T> u, Node<T> v, boolean inserted
	) {
		super(source);

		mU = u;
		mV = v;
		mInserted = inserted;
	}

	public Node<T> getFirst () {
		return mU;
	}

	public Node<T> getSecond () {
		return mV;
	}

	public boolean wasInserted () {
		return mInserted;
	}

	public boolean wasDeleted() {
		return mInserted;
	}
}
