package unibo.algat.view;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import unibo.algat.graph.Graph;
import unibo.algat.graph.Node;
import unibo.algat.graph.WeightFunction;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>Common abstract base class implemented by all those {@link LessonView}s
 * having to deal with a {@link Graph} data structure.</p>
 */
public abstract class GraphLessonView<T> extends LessonView {
	@FXML protected GraphView<T> mGraphV;
	protected WeightFunction<T> mWeight;
	/**
	 * Used by the "add node" functionality, to track the next node id to
	 * insert into the graph.
	 */
	private int mNextId = 0;

	private final EventHandler<ActionEvent> mRemoveAction = e -> {
		final List<NodeView> toDelete = List.copyOf(
			mGraphV.mNodeSelection.getSelectedItems()
		);
		final Graph<T> g = mGraphV.getGraph();

		toDelete.forEach(v -> g.deleteNode((Node<T>) v.getNode()));
	};

	public GraphLessonView () throws IOException {
		super();

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
		// Reset the next id value to 0 when the graph is set to null, or to
		// the least possible id when it changes
		mGraphV.graphProperty().addListener(graph -> {
			if (mGraphV.getGraph() == null) {
				mNextId = 0;
			} else {
				mNextId = mGraphV.getGraph().nodes().size();
			}
		});
	}

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
		super.onAcquireToolBar(toolBar);
		// Common condition disabling a number of toolbar buttons
		final BooleanBinding baseStop = mGraphV.graphProperty().isNull().or(
			mAlgo.runningProperty().or(mAlgo.stoppedProperty())
		);

		// Disable properties section
        toolBar.getAddButton().disableProperty().bind(baseStop);
		toolBar.getRemoveButton().disableProperty().bind(baseStop.or(
			mGraphV.mNodeSelection.itemCountProperty().lessThanOrEqualTo(0)
		));
		toolBar.getClearButton().disableProperty().bind(baseStop);

		// Button actions section
		toolBar.getAddButton().setOnAction(e ->
			mGraphV.getGraph().insertNode(new Node<>(mNextId++))
		);
		toolBar.getRemoveButton().setOnAction(mRemoveAction);
		toolBar.getClearButton().setOnAction(e -> mGraphV.getGraph().clear());
	}
}
