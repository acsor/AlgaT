package unibo.algat.algorithm;

import unibo.algat.graph.Node;
import java.util.PriorityQueue;
import static unibo.algat.algorithm.SerialExecutor.runAndWait;


/**
 * Dijkstra serial algorithm implementation.
 */
public final class DijkstraAlgorithm extends ShortestPathAlgorithm {
	@Override
	protected Void call() {
		// TODO Update with a priority queue such as it was in actual use during
		//  Dijkstra's times.
		final PriorityQueue<Node<Double>> q = new PriorityQueue<>(
			mGraph.nodes().size()
		);

		// The UI thread prefers to receive as few as possible (and as small
		// as possible) tasks
		runAndWait(() -> {
			for (Node<Double> u: mGraph.nodes()) {
				if (!u.equals(mRoot))
					u.setData(Double.POSITIVE_INFINITY);
			}

			mRoot.setData(0.0);
		});

		q.add(mRoot);

		setBreakpoint();

		while (!q.isEmpty()) {
			Node<Double> u = q.remove();

			for (Node<Double> v: mGraph.adjacents(u)) {
				setBreakpoint();

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
