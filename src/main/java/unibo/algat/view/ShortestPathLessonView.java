package unibo.algat.view;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import unibo.algat.algorithm.ShortestPathAlgorithm;
import unibo.algat.graph.ALGraph;
import unibo.algat.graph.Node;
import unibo.algat.graph.RandomALGraphFactory;
import unibo.algat.graph.RandomMWFFactory;

import java.io.IOException;

/**
 * <p>Common abstract base class implemented by all those
 * {@link GraphLessonView}s having to deal with shortest path algorithms.</p>
 * <p>Since shortest path algorithms deal with real-valued weights, this
 * class fixes the type parameter of {@link GraphLessonView} to {@code Double}.
 * </p>
 */
public abstract class ShortestPathLessonView extends GraphLessonView<Double> {
	protected ShortestPathAlgorithm mSPAlgo;

	/**
	 * Callback invoked during the press of the toolbar "random button".
	 */
	private final EventHandler<ActionEvent> mRandomAction = event -> {
		mGraphV.setGraph(new RandomALGraphFactory<>(15, 10));
		mGraphV.setWeightFunction(
			new RandomMWFFactory<Double>(0, 25).make(mGraphV.getGraph())
		);

		mSPAlgo.setGraph(mGraphV.getGraph());
		mSPAlgo.setWeightFunction(mGraphV.getWeightFunction());
	};

	public ShortestPathLessonView() throws IOException {
		super();
		mSPAlgo = (ShortestPathAlgorithm) mAlgo;
	}

	protected abstract ShortestPathAlgorithm algorithmFactory();

	@FXML
	protected void initialize () {
		super.initialize();

		mGraphV.setGraph(new ALGraph<>());
		mGraphV.setNodeInfoType(GraphView.NodeInfoType.value);
		mGraphV.mNodeSelection.itemCountProperty().addListener(o -> {
			if (mGraphV.mNodeSelection.getItemCount() == 1) {
				mSPAlgo.setRoot((Node<Double>)
					mGraphV.mNodeSelection.getSelectedItems().get(0).getNode()
				);
			} else {
				mSPAlgo.setRoot(null);
			}
		});
	}

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
		super.onAcquireToolBar(toolBar);
		final BooleanBinding baseStop = mAlgo.runningProperty().or(
			mAlgo.stoppedProperty()
		);

		toolBar.getRandomButton().disableProperty().bind(
			baseStop.or(mAlgo.runningProperty())
		);
		toolBar.getRandomButton().setOnAction(mRandomAction);
	}
}
