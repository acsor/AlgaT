package unibo.algat.graph;

/**
 * <p>Abstract Factory class to build {@link Graph} instances.</p>
 */
public interface GraphFactory<T> {
    ALGraph<T> ALGraphFactory();
}
