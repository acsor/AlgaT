package unibo.algat.graph;

public class MemoryEdgeWeight<T> extends EdgeWeight<T> {
	private double mWeight;

	public MemoryEdgeWeight(Vertex<T> a, Vertex<T> b, double weight) {
		super(a, b);
		setWeight(weight);
	}

	public final void setWeight (double weight) {
        mWeight = weight;
        invalidate();
	}

	@Override
	protected double computeValue() {
		return mWeight;
	}
}
