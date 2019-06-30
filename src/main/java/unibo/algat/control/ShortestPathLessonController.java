package unibo.algat.control;

import javafx.fxml.FXML;
import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;
import unibo.algat.view.GraphView;

public class ShortestPathLessonController {
	@FXML private GraphView<String> mGraphView;

	@FXML
	void initialize () {
		Graph<String> g;

		mGraphView.setGraph(new ALGraph<>());
		g = mGraphView.getGraph();

		for (int i = 0; i < 20; i++) {
			g.insertNode(new Node<>(i));
		}

		g.insertEdge(new Node<>(1), new Node<>(4));
		g.insertEdge(new Node<>(3), new Node<>(0));
		g.insertEdge(new Node<>(2), new Node<>(1));
		g.insertEdge(new Node<>(9), new Node<>(8));
		g.insertEdge(new Node<>(0), new Node<>(7));
		g.insertEdge(new Node<>(4), new Node<>(5));
	}
}
