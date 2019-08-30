package unibo.algat.algorithm;

import unibo.algat.graph.Vertex;

import java.util.PriorityQueue;
import java.util.Queue;


/**
 * Bellman-Ford-Moore serial algorithm implementation.
 */
public class BFMAlgorithm extends ShortestPathAlgorithm {
	@Override
	protected Void call() throws Exception {
		final Queue<Vertex<Double>> q = new PriorityQueue<>(
			mGraph.vertices().size()
		);

		runAndWait(() -> {
			for (Vertex<Double> u: mGraph.vertices()) {
				if (!u.equals(mRoot))
					u.setData(Double.POSITIVE_INFINITY);
			}

			mRoot.setData(0.0);
		});

		q.add(mRoot);

		setBreakpoint();

		while (!q.isEmpty()) {
			Vertex<Double> u = q.remove();

			for (Vertex<Double> v: mGraph.adjacents(u)) {
				setBreakpoint();

				mOnVisitEdge.accept(u, v);

				if (u.getData() + mWeights.weight(u, v) < v.getData()) {
					if (!q.contains(v))
						q.add(v);

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
