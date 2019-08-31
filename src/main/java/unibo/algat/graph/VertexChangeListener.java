package unibo.algat.graph;

import java.util.EventListener;

public interface VertexChangeListener<T> extends EventListener {
    /**
     * <p>Called to notify that a {@code Graph} listened to was subject to a
     * vertex change.</p>
     * @param e The {@link VertexChangeEvent} instance encapsulating the event
     * data.
     */
    void vertexChanged(VertexChangeEvent<T> e);
}
