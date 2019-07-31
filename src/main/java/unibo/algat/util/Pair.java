package unibo.algat.util;

/**
 * A class encapsulating data about a generic pair of objects of type {@code
 * T} and {@code U}.
 *
 * @param <T> Type of the first object
 * @param <U> Type of the second object
 */
public class Pair<T, U> {
	private T mFirst;
	private U mSecond;

	public Pair(T first, U second) {
		mFirst = first;
		mSecond = second;
	}

	public T getFirst() {
		return mFirst;
	}

	public U getSecond() {
		return mSecond;
	}

	@Override
	public int hashCode () {
		// TODO Verify that the hashCode() implementation is proper
		return mFirst.hashCode() ^ mSecond.hashCode();
	}

	@Override
	public boolean equals (Object other) {
		Pair<T, U> o;

		if (other instanceof Pair) {
			// TODO How to check that other type arguments are the same as
			// 	the current instance?
			o = (Pair<T, U>) other;

			return mFirst == o.mFirst && mSecond == o.mSecond;
		}

		return false;
	}

	@Override
	public String toString () {
		return String.format(
			"%s [mFirst=%s] [mSecond=%s]", getClass().getName(), mFirst, mSecond
		);
	}
}
