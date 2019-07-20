package unibo.algat.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import unibo.algat.graph.*;

import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;

public class ShortestPathLessonView extends LessonView implements ToolBarUser {
	private ObjectProperty<Graph<Integer>> mGraph;
	private int mMaxId;

	private ResourceBundle mInterface;

	@FXML private GraphView<Integer> mGraphView;

	public ShortestPathLessonView () throws IOException {
		super();

		mGraph = new SimpleObjectProperty<>(this, "graph");
		mMaxId = 0;
		mInterface = ResourceBundle.getBundle("Interface");
		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/ShortestPathLessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
		final Random r = new Random(System.currentTimeMillis());

        toolBar.getAddButton().disableProperty().bind(mGraph.isNull());
		toolBar.getAddButton().setTooltip(
			new Tooltip(mInterface.getString("gui.splv.tooltip.add"))
		);
		toolBar.getAddButton().setOnAction(
			event -> mGraph.get().insertNode(new Node<>(++mMaxId))
		);

		toolBar.getRandomButton().setDisable(false);
		toolBar.getRandomButton().setTooltip(
			new Tooltip(mInterface.getString("gui.splv.tooltip.random"))
		);
		toolBar.getRandomButton().setOnAction(event -> {
			RandomGraphFactory<Integer> factory = new RandomALGraphFactory<>(
				20, 10
			);
			mMaxId = 20;

			factory.setValueFactory(() -> r.nextInt(50));
			mGraphView.setGraph(factory);
			mGraphView.setWeightFunction(
				new DifferentialWeightFunction<>(mGraphView.getGraph())
			);
			mGraph.set(mGraphView.getGraph());
		});

		// TODO Add number of vertices property in Graph, so that one can be
		//  notified about their number
		toolBar.getClearButton().disableProperty().bind(mGraph.isNull());
		toolBar.getClearButton().setTooltip(
			new Tooltip(mInterface.getString("gui.splv.tooltip.clear"))
		);
		toolBar.getClearButton().setOnAction(event -> {
			mGraph.get().nodes().forEach(node -> mGraph.get().deleteNode(node));
			mMaxId = 0;
		});
	}
}
