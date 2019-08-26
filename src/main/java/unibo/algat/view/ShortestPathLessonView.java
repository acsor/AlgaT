package unibo.algat.view;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import unibo.algat.algorithm.SerialAlgorithm;
import unibo.algat.algorithm.ShortestPathAlgorithm;
import unibo.algat.graph.*;

import java.io.IOException;
import java.util.function.Function;

import static unibo.algat.algorithm.SerialAlgorithm.runAndWait;

/**
 * <p>Common abstract base class implemented by all those
 * {@link GraphLessonView}s having to deal with shortest path algorithms.</p>
 * <p>Since shortest path algorithms deal with real-valued weights, this
 * class fixes the type parameter of {@link GraphLessonView} to {@code Double}.
 * </p>
 */
public abstract class ShortestPathLessonView extends GraphLessonView<Double> {
	protected ShortestPathAlgorithm mSPAlgo;

	// Better cache these
	private final GraphFactory<Double> mGraphFactory =
		new RandomALGraphFactory<>(15, 20);
	private final WeightFunctionFactory<Double> mWeightFactory =
		new RandomMWFFactory<>(0, 25);

	/**
	 * A node formatter showing a node id on top, and its distance from the
	 * root node at bottom.
	 *
	 * @see GraphView#setNodeFormatter
	 */
	private static final Function<Node<Double>, StringBinding> sNodeFormatter =
		node -> new StringBinding() {
			{ bind(node.dataProperty()); }

			@Override
			protected String computeValue() {
				final Double value = node.getData();
				String repr;

				if (value == null)
					repr = "";
				else if (value == Double.POSITIVE_INFINITY)
					repr = "\u221e";	// Infinity Unicode code point
				else
					repr = String.format("%.2f", value);

				return String.format("%d\n%s", node.getId(), repr);
			}
		};

	/**
	 * Callback invoked during the press of the toolbar "random button".
	 */
	private final EventHandler<ActionEvent> mRandomAction = event -> {
		mGraphV.setGraph(mGraphFactory.make());
		mGraphV.setWeightFunction(mWeightFactory.make(mGraphV.getGraph()));

		mSPAlgo.setGraph(mGraphV.getGraph());
		mSPAlgo.setWeightFunction(mGraphV.getWeightFunction());
	};

	public ShortestPathLessonView() throws IOException {
		super();
		mSPAlgo = (ShortestPathAlgorithm) mAlgo;

		mSPAlgo.setOnVisitEdgeAction(
			// It looks like removing the runAndWait() part has no effect on
			// whether the fadeEdge() method is successfully executed on the
			// background thread. Does this mean that JavaFX transitions have
			// no need to be offloaded on the UI thread?
			(u, v) -> runAndWait(() -> mGraphV.fadeEdge(u, v))
		);
	}

	protected abstract ShortestPathAlgorithm algorithmFactory();

	@FXML
	protected void initialize () {
		super.initialize();

		mGraphV.setGraph(new ALGraph<>());
		mGraphV.setNodeFormatter(sNodeFormatter);
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
