package unibo.algat.util;

import unibo.algat.graph.RandomGraphFactory;

/**
 * <p>An interface for objects generating values of type {@code T}
 * arbitrarily (randomly or according to some given criteria).</p>
 * @param <T> Type of the value to produce
 * @see RandomGraphFactory
 */
public interface ValueFactory<T> {
	T makeValue ();
}
