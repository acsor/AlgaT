package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import unibo.algat.graph.DifferentialWeightFunction;
import unibo.algat.graph.RandomALGraphFactory;

import java.io.IOException;
import java.util.Random;

public class ShortestPathLessonView extends LessonView {
	@FXML private GraphView<Integer> mGraphView;

	public ShortestPathLessonView () throws IOException {
		super();
        // TODO Find a way of initializing ShortestPathLessonView through
		//  FXML -- apparently Java Generics were preventing mGraphView from
		//  being fetched from the .fxml file
	}

	@FXML
	protected void initialize () {
		super.initialize();

		final Random r = new Random(System.currentTimeMillis());
		final RandomALGraphFactory<Integer> factory =
			new RandomALGraphFactory<>(20,	10);
		mGraphView = new GraphView<>();

		factory.setValueFactory(() -> r.nextInt(20));

		mGraphView.setGraph(factory);
		mGraphView.setWeightFunction(
			new DifferentialWeightFunction<>(mGraphView.getGraph())
		);
		mGraphView.setNodeRadius(25);
		mGraphView.setNodeMargin(10);
		mGraphView.setPadding(new Insets(10));
		mGraphView.setGraphLayout(new GraphGridLayout(4));
		mGraphView.setNodeFill(Color.rgb(62, 134, 160));

		setCenter(mGraphView);
	}
}
