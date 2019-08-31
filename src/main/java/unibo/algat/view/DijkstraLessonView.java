package unibo.algat.view;

import unibo.algat.algorithm.DijkstraAlgorithm;
import unibo.algat.algorithm.ShortestPathAlgorithm;

import java.io.IOException;

public class DijkstraLessonView extends ShortestPathLessonView {
	public DijkstraLessonView() throws IOException {
	}

	protected ShortestPathAlgorithm algorithmFactory() {
		return new DijkstraAlgorithm();
	}
}
