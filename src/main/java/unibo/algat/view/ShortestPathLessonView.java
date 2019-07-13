package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import unibo.algat.graph.DifferentialWeightFunction;
import unibo.algat.graph.RandomALGraphFactory;

import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;

public class ShortestPathLessonView extends LessonView {
	@FXML private GraphView<Integer> mGraphView;

	public ShortestPathLessonView () throws IOException {
		super();

		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/ShortestPathLessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	protected void initialize () {
		final Random r = new Random(System.currentTimeMillis());
		final RandomALGraphFactory<Integer> factory =
			new RandomALGraphFactory<>(20,	10);

		factory.setValueFactory(() -> r.nextInt(20));

		mGraphView.setGraph(factory);
		mGraphView.setWeightFunction(
			new DifferentialWeightFunction<>(mGraphView.getGraph())
		);
	}
}
