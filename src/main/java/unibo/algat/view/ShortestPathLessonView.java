package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import unibo.algat.graph.RandomALGraphFactory;

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
		mGraphView = new GraphView<>();

		mGraphView.setGraph(new RandomALGraphFactory<>(20, 10));
		mGraphView.setNodeRadius(25);
		mGraphView.setNodeMargin(10);
		mGraphView.setPadding(new Insets(10));
		mGraphView.setGraphLayout(new GraphGridLayout(4));
		mGraphView.setNodeFill(Color.rgb(62, 134, 160));

		setCenter(mGraphView);
	}
}
