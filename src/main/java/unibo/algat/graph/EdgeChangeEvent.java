package unibo.algat.graph;

import java.util.EventObject;

public class EdgeChangeEvent<T> extends EventObject {
	private Vertex<T> mU, mV;
	private boolean mInserted;

	public EdgeChangeEvent(
		Object source, Vertex<T> u, Vertex<T> v, boolean inserted
	) {
		super(source);

		mU = u;
		mV = v;
		mInserted = inserted;
	}

	public Vertex<T> getFirst () {
		return mU;
	}

	public Vertex<T> getSecond () {
		return mV;
	}

	public boolean wasInserted () {
		return mInserted;
	}

	public boolean wasDeleted() {
		return !mInserted;
	}
}
