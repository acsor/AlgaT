package unibo.algat.graph;

import unibo.algat.util.Pair;

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

	public Pair<Node<T>, Node<T>> getEdge () {
		return new Pair<>(mU, mV);
	}

	public boolean wasInserted () {
		return mInserted;
	}

	public boolean wasRemoved () {
		return mInserted;
	}
}
