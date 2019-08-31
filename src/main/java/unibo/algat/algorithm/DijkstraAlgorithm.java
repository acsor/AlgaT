package unibo.algat.algorithm;

import unibo.algat.graph.Vertex;

import java.util.PriorityQueue;


/**
 * Dijkstra serial algorithm implementation.
 */
public final class DijkstraAlgorithm extends ShortestPathAlgorithm {
	@Override
	protected Void call() {
		// TODO Update with a priority queue such as it was in actual use during
		//  Dijkstra's times.
		final PriorityQueue<Vertex<Double>> q = new PriorityQueue<>(
			mGraph.vertices().size()
		);

		// The UI thread prefers to receive as few as possible (and as small
		// as possible) tasks
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
					if (!q.contains(v)) {
						q.add(v);
					} else {
						q.remove(v);
						q.add(v);
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
