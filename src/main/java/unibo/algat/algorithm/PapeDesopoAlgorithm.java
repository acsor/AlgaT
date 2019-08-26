package unibo.algat.algorithm;

import unibo.algat.graph.Node;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A Pape-D'Esopo shortest path algorithm implementation.
 */
public class PapeDesopoAlgorithm extends ShortestPathAlgorithm {
	@Override
	protected Void call() throws Exception {
		final Deque<Node<Double>> d = new LinkedList<>();

		runAndWait(() -> {
			for (Node<Double> u: mGraph.nodes()) {
				if (!u.equals(mRoot))
					u.setData(Double.POSITIVE_INFINITY);
			}

			mRoot.setData(0.0);
		});

		d.addFirst(mRoot);

		setBreakpoint();

		while (!d.isEmpty()) {
			Node<Double> u = d.removeFirst();

			for (Node<Double> v: mGraph.adjacents(u)) {
				setBreakpoint();

				mOnVisitEdge.accept(u, v);

				if (u.getData() + mWeights.weight(u, v) < v.getData()) {
					if (!d.contains(v)) {
						if (v.getData() == Double.POSITIVE_INFINITY)
							d.addLast(v);
						else
							d.addFirst(v);
					}

					// T[v] <- u
					runAndWait(() ->
						v.setData(u.getData() + mWeights.weight(u, v))
					);
				}
			}
		}

		return null;
	}
}
