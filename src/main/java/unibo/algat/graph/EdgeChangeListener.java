package unibo.algat.graph;

import java.util.EventListener;

public interface EdgeChangeListener<T> extends EventListener {
	void edgeChanged(EdgeChangeEvent<T> e);
}
