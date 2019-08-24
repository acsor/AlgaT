package unibo.algat.view;

import unibo.algat.algorithm.PapeDesopoAlgorithm;
import unibo.algat.algorithm.ShortestPathAlgorithm;

import java.io.IOException;

/**
 * A Pape-D'Esopo shortest path algorithm simulation.
 */
public class PapeDesopoLessonView extends ShortestPathLessonView {
	public PapeDesopoLessonView() throws IOException {
	}

	@Override
	protected ShortestPathAlgorithm algorithmFactory() {
		return new PapeDesopoAlgorithm();
	}
}
