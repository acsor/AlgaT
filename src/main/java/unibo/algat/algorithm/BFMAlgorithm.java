package unibo.algat.algorithm;

import unibo.algat.graph.Node;

import java.util.PriorityQueue;
import java.util.Queue;


/**
 * Bellman-Ford-Moore serial algorithm implementation.
 */
public class BFMAlgorithm extends ShortestPathAlgorithm {
	@Override
	protected Void call() throws Exception {
		final Queue<Node<Double>> q = new PriorityQueue<>(
			mGraph.nodes().size()
		);

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
