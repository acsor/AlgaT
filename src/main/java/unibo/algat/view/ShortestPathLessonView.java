package unibo.algat.view;

import javafx.application.Platform;
import unibo.algat.algorithm.SerialAlgorithm;
import unibo.algat.graph.Node;

import java.io.IOException;

public class ShortestPathLessonView extends GraphLessonView {
	public ShortestPathLessonView() throws IOException {
	}

	protected SerialAlgorithm<?> algorithmFactory() {
		return new SerialAlgorithm<Void>() {
			@Override
			protected Void call() {
				for (Node<Integer> node: mGraph.get().nodes()) {
					try {
						setBreakpoint();
					} catch (InterruptedException ignored) {
					}

					Platform.runLater(() -> mGraph.get().deleteNode(node));
				}

				return null;
			}
		};
	}
}
