package unibo.algat.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;

import java.io.IOException;

public class ShortestPathLessonView extends LessonView {
	@FXML private GraphView<String> mGraphView;

	public ShortestPathLessonView () throws IOException {
		super();
        // TODO Find a way of initializing ShortestPathLessonView through
		//  FXML -- apparently Java Generics were preventing mGraphView from
		//  being fetched from the .fxml file
	}

	@FXML
	protected void initialize () {
		super.initialize();
		Graph<String> g;
		mGraphView = new GraphView<>();

		mGraphView.setPrefWidth(500);
		mGraphView.setPrefHeight(500);
		mGraphView.setNodeRadius(25);
		mGraphView.setNodeMargin(25);
		mGraphView.setPadding(new Insets(10));
		mGraphView.setGraphLayout(new GraphGridLayout(4));
		mGraphView.setNodeFill(Color.rgb(62, 134, 160));

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

		setCenter(mGraphView);

		mControls.getTogglePlayButton().setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Toggle button was pressed!");
			}
		});
	}
}
