package unibo.algat.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	private Random mRandom;

	private ResourceBundle mInterface;
	@FXML private GraphView<Integer> mGraphView;

	private final EventHandler<ActionEvent> mRandomAction = event -> {
		RandomGraphFactory<Integer> factory = new RandomALGraphFactory<>(
			20, 10
		);
		mMaxId = 20;

		factory.setValueFactory(() -> mRandom.nextInt(50));
		mGraphView.setGraph(factory);
		mGraphView.setWeightFunction(
			new DifferentialWeightFunction<>(mGraphView.getGraph())
		);
		mGraph.set(mGraphView.getGraph());
	};
	private final EventHandler<ActionEvent> mClearAction = event -> {
		mGraph.get().nodes().forEach(node -> mGraph.get().deleteNode(node));
		mMaxId = 0;
	};

	public ShortestPathLessonView () throws IOException {
		super();

		mGraph = new SimpleObjectProperty<>(this, "graph");
		mMaxId = 0;
		mRandom = new Random(System.currentTimeMillis());

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
		toolBar.getRandomButton().setOnAction(mRandomAction);

		// TODO Add number of vertices property in Graph, so that one can be
		//  notified about their number
		toolBar.getClearButton().disableProperty().bind(mGraph.isNull());
		toolBar.getClearButton().setTooltip(
			new Tooltip(mInterface.getString("gui.splv.tooltip.clear"))
		);
		toolBar.getClearButton().setOnAction(mClearAction);
	}

	@Override
	public void onReleaseToolBar(AlgaToolBar toolBar) {
		toolBar.getAddButton().disableProperty().unbind();
		toolBar.getClearButton().disableProperty().unbind();
	}
}
