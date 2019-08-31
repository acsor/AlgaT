package unibo.algat.view;

import unibo.algat.algorithm.BFMAlgorithm;
import unibo.algat.algorithm.ShortestPathAlgorithm;

import java.io.IOException;

/**
 * A Bellman-Ford-Moore algorithm simulation.
 */
public class BFMLessonView extends ShortestPathLessonView {
	public BFMLessonView() throws IOException {
	}

	@Override
	protected ShortestPathAlgorithm algorithmFactory() {
		return new BFMAlgorithm();
	}
}
