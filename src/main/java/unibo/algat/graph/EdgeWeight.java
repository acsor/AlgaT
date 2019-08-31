package unibo.algat.graph;

import javafx.beans.binding.DoubleBinding;

public abstract class EdgeWeight<T> extends DoubleBinding {
	protected Vertex<T> mA, mB;

	public EdgeWeight(Vertex<T> a, Vertex<T> b) {
		mA = a;
		mB = b;
	}

	public Vertex<T> getFirst () {
		return mA;
	}

	public Vertex<T> getSecond () {
		return mB;
	}
}
