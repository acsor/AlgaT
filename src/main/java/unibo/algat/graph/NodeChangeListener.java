package unibo.algat.graph;

import java.util.EventListener;

public interface NodeChangeListener<T> extends EventListener {
    /**
     * <p>Called to notify that a {@code Graph} listened to was subject to a
     * node change.</p>
     * @param e The {@link NodeChangeEvent} instance encapsulating the event
     *          data
     */
    void nodeChanged (NodeChangeEvent e);
}
