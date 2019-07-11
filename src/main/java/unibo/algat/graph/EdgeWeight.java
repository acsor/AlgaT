package unibo.algat.graph;

import javafx.beans.binding.DoubleBinding;

public abstract class EdgeWeight<T> extends DoubleBinding {
	protected Node<T> mA, mB;

	public EdgeWeight(Node<T> a, Node<T> b) {
		mA = a;
		mB = b;
	}

	public Node<T> getFirst () {
		return mA;
	}

	public Node<T> getSecond () {
		return mB;
	}
}
