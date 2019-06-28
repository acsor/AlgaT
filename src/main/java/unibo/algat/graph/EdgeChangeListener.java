package unibo.algat.graph;

public interface EdgeChangeListener<T> {
	void edgeChanged(EdgeChangeEvent<T> e);
}
