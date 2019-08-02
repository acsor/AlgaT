package unibo.algat.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import unibo.algat.graph.*;

import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * <p>Common base class implemented by all those {@link LessonView}s having
 * to deal with a {@link Graph} data structure.</p>
 */
public abstract class GraphLessonView extends LessonView {
	protected ObjectProperty<ObservableGraph<Integer>> mGraph;
	private int mMaxId;
	private Random mRandom;

	@FXML protected GraphView<Integer> mGraphView;

	private final EventHandler<ActionEvent> mRandomAction = event -> {
		RandomGraphFactory<Integer> factory = new RandomALGraphFactory<>(20, 10);
		mMaxId = 20;

		factory.setValueFactory(() -> mRandom.nextInt(50));
		mGraphView.setGraph(factory);
		mGraphView.setWeightFunction(
			new DifferentialWeightFunction<>(mGraphView.getGraph())
		);
	};
	private final EventHandler<ActionEvent> mClearAction = event -> {
		mGraph.get().nodes().forEach(mGraph.get()::deleteNode);
		mMaxId = 0;
		mGraphView.setGraph((Graph<Integer>) null);
	};

	public GraphLessonView () throws IOException {
		super();

		mGraph = new SimpleObjectProperty<>(this, "graph");
		mMaxId = 0;
		mRandom = new Random(System.currentTimeMillis());

		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/GraphLessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	protected void initialize () {
		mGraph.bind(mGraphView.graphProperty());
	}

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
		super.onAcquireToolBar(toolBar);

		toolBar.getStopButton().disableProperty().bind(
			mAlgo.stoppedProperty().or(mGraph.isNull())
		);
		toolBar.getPlayButton().disableProperty().bind(
			mAlgo.stoppedProperty().or(mGraph.isNull())
		);
		toolBar.getNextButton().disableProperty().bind(
			mAlgo.stoppedProperty().or(mExecutor.autoRunningProperty()).or(
				mGraph.isNull()
			)
		);

        toolBar.getAddButton().disableProperty().bind(
        	mGraph.isNull().or(mAlgo.stoppedProperty())
		);
		toolBar.getAddButton().setOnAction(
			event -> mGraph.get().insertNode(new Node<>(++mMaxId))
		);

		toolBar.getRandomButton().disableProperty().bind(
			mAlgo.stoppedProperty()
		);
		toolBar.getRandomButton().setOnAction(mRandomAction);

		// TODO Add number of vertices property in Graph, so that one can be
		//  notified about their number
		toolBar.getClearButton().disableProperty().bind(
			mGraph.isNull().or(mAlgo.stoppedProperty())
		);
		toolBar.getClearButton().setOnAction(mClearAction);
	}
}
