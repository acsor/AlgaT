package unibo.algat.view;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import unibo.algat.graph.*;
import unibo.algat.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>A view class responsible for displaying a {@link Graph} object.</p>
 */
public class GraphView<T> extends Region {
	private ObjectProperty<ObservableGraph<T>> mGraph;
	private WeightFunction<T> mWeights;

	Map<Node<T>, NodeView> mNodes;
	private Map<Pair<Node<T>, Node<T>>, EdgeView> mEdges;
	private Map<Pair<Node<T>, Node<T>>, WeightView> mWeightViews;
	GraphLayout mLayout;
	GraphNodeSelectionModel mNodeSelection;

	private double mNodeRadius, mNodeMargin;
	private Paint mNodeFill;
	private Function<Node<T>, StringBinding> mNodeFormatter;

	private static final double DEFAULT_NODE_RADIUS = 1;
	private static final double DEFAULT_NODE_MARGIN = 1;

	private static final double NODE_VIEW_ORDER = 1;
	private static final double WEIGHT_LABEL_VIEW_ORDER = 2;
	private static final double EDGE_VIEW_ORDER = 3;

	private final NodeChangeListener<T> mNodeListener = e -> {
		if (e.wasInserted())
			addNodeView(e.getNode());
		else if (e.wasDeleted())
			removeNodeView(e.getNode());
	};
	private final EdgeChangeListener<T> mEdgeListener = e -> {
		if (e.wasInserted())
			addEdgeView(e.getFirst(), e.getSecond());
		else if (e.wasDeleted())
			removeEdgeView(e.getFirst(), e.getSecond());
	};

	/**
	 * A node formatter associating a {@link Node} to its id.
	 *
	 * @see #setNodeFormatter
	 */
	public final Function<Node<T>, StringBinding> NODE_ID_FORMATTER = node ->
		new StringBinding() {
			@Override
			protected String computeValue() {
				return String.valueOf(node.getId());
			}
		};

	public GraphView () {
		mGraph = new SimpleObjectProperty<>(this, "graph");
		mNodes = new HashMap<>();
		mEdges = new HashMap<>();
		mWeightViews = new HashMap<>();

		mLayout = new GraphGridLayout(6);
		mNodeSelection = new GraphNodeSelectionModel(this);
		mNodeFormatter = NODE_ID_FORMATTER;

		mNodeRadius = DEFAULT_NODE_RADIUS;
		mNodeMargin = DEFAULT_NODE_MARGIN;
	}

	public void setGraph(Graph<T> graph) {
		if (mGraph.get() != null) {
			mGraph.get().removeNodeChangeListener(mNodeListener);
			mGraph.get().removeEdgeChangeListener(mEdgeListener);
		}

		mNodeSelection.clearSelection();
		mLayout.clear();
		mWeightViews.clear();
		setWeightFunction(null);
		mEdges.clear();
		mNodes.clear();
		getChildren().clear();

		if (graph != null) {
			mGraph.set(new ObservableGraph<>(graph));
			mGraph.get().addNodeChangeListener(mNodeListener);
			mGraph.get().addEdgeChangeListener(mEdgeListener);

			for (Node<T> u: mGraph.get().nodes())
				addNodeView(u);

			for (Node<T> u: mGraph.get().nodes()) {
				for (Node<T> v: mGraph.get().adjacents(u))
					addEdgeView(u, v);
			}
		} else {
			mGraph.set(null);
		}
	}

	public Graph<T> getGraph () {
		return mGraph.get();
	}

	public ObjectProperty<ObservableGraph<T>> graphProperty () {
		return mGraph;
	}

	public void setWeightFunction(WeightFunction<T> w) {
        mWeights = w;

        if (w != null)
			mWeightViews.forEach((pair, view) ->
				view.setWeight(w.weightBinding(pair.getFirst(), pair.getSecond()))
			);
	}

	public WeightFunction<T> getWeightFunction() {
		return mWeights;
	}

	public void setNodeRadius(double radius) {
		mNodeRadius = radius;

		for (NodeView v: mNodes.values())
			v.setRadius(radius);
	}

	public double getNodeRadius() {
		return mNodeRadius;
	}

	public void setNodeMargin (double margin) {
		mNodeMargin = margin;

		for (NodeView v: mNodes.values())
			v.setPadding(new Insets(margin));
	}

	public double getNodeMargin() {
		return mNodeMargin;
	}

	public void setNodeFill (Paint nodeFill) {
		mNodeFill = nodeFill;

		for (NodeView v: mNodes.values())
			v.setFill(nodeFill);
	}

	public Paint getNodeFill () {
		return mNodeFill;
	}

	/**
	 * Specifies a <i>node formatter</i>, that is a function taking in an
	 * instance of {@link Node} and producing a {@link StringBinding} serving
	 * as its textual representation within a {@link NodeView}.
	 */
	public void setNodeFormatter (Function<Node<T>, StringBinding> formatter) {
		mNodeFormatter = formatter;
	}

	/**
	 * @return The node formatter in current use by this {@code GraphView}
	 * object.
	 *
	 * @see #setNodeFormatter
	 */
	public Function<Node<T>, StringBinding> getNodeFormatter () {
		return mNodeFormatter;
	}

	public void setGraphLayout (GraphLayout layout) {
		mNodeSelection.clearSelection();

		mLayout = layout;
        requestLayout();
	}

	public GraphLayout getGraphLayout () {
		return mLayout;
	}

	/**
	 * Invokes a fade in animation on edge {@code (u, v)}.
	 */
	public void fadeEdge (Node<T> u, Node<T> v) {
		mEdges.get(new Pair<>(u, v)).fadeIn();
	}

	@Override
	protected void layoutChildren () {
		final Insets bounds = getInsets();

		for (NodeView view: mNodes.values()) {
			final Point2D location = mLayout.layout(view);

			setPrefWidth(Math.max(
				getPrefWidth(), bounds.getLeft() + bounds.getLeft() +
					location.getX() + view.getWidth()
			));
			setPrefHeight(Math.max(
				getPrefHeight(), bounds.getTop() + bounds.getBottom() +
					location.getY() + view.getHeight()
			));

			layoutInArea(
				view,
				location.getX() + bounds.getLeft(),
				location.getY() + bounds.getTop(),
				view.computePrefWidth(-1), view.computePrefHeight(-1),
				0, HPos.CENTER, VPos.CENTER
			);
		}

		// Layout edge weight labels
		for (WeightView view: mWeightViews.values()) {
			Point2D location = view.getEdgeView().getTop();
			layoutInArea(
                view,
				bounds.getLeft() + location.getX(),
				bounds.getTop() + location.getY(),
				view.prefWidth(-1), view.prefHeight(-1),
				0, HPos.CENTER, VPos.CENTER
			);
		}
	}

	private void addNodeView (Node<T> node) {
		final NodeView view = new NodeView(node);

		view.textProperty().bind(mNodeFormatter.apply(node));
		view.setRadius(mNodeRadius);
		view.setFill(mNodeFill);
		view.setPadding(new Insets(mNodeMargin));
		view.setViewOrder(NODE_VIEW_ORDER);

		// When a NodeView is selected by a click, add it to the selection model
		view.selectedProperty().addListener((o, wasSelected, isSelected) -> {
			if (!wasSelected && isSelected)
				mNodeSelection.select(view);
			else if (wasSelected && !isSelected)
				mNodeSelection.clearSelection(view);
		});

        mNodes.put(node, view);
        getChildren().add(view);
	}

	private void removeNodeView (Node<T> node) {
		final NodeView removed = mNodes.remove(node);

		if (removed != null) {
			mNodeSelection.clearSelection(removed);
			mLayout.remove(removed);
			getChildren().remove(removed);
		}
	}

	private void addEdgeView(Node<T> u, Node<T> v) {
		final EdgeView edge = new EdgeView(mNodes.get(u), mNodes.get(v));
		final WeightView weightView = new WeightView(
			edge, mWeights != null ? mWeights.weightBinding(u, v): null
		);

		edge.setViewOrder(EDGE_VIEW_ORDER);
		weightView.setViewOrder(WEIGHT_LABEL_VIEW_ORDER);

		mEdges.put(new Pair<>(u, v), edge);
		mWeightViews.put(new Pair<>(u, v), weightView);

		getChildren().addAll(edge, weightView);
	}

	private void removeEdgeView(Node<T> u, Node<T> v) {
		EdgeView edge = mEdges.remove(new Pair<>(u, v));
		WeightView weight = mWeightViews.remove(new Pair<>(u, v));

		if (edge != null)
			getChildren().remove(edge);

		if (weight != null)
			getChildren().remove(weight);
	}

	@Override
	public String toString () {
		final StringBuilder b = new StringBuilder();

		b.append(String.format(
			"[%s vertices=%d, edges=%d]\n",getClass().getSimpleName(),
			mNodes == null ? 0: mNodes.keySet().size(),
			mEdges == null ? 0: mEdges.keySet().size()
		));

		b.append(String.format("getChildren() = %d\n", getChildren().size()));
        b.append(String.format("mNodes = %s\n", String.valueOf(mNodes)));
		b.append(String.format("mEdges = %s\n", String.valueOf(mEdges)));
		b.append(
			String.format("mWeightViews = %s\n", String.valueOf(mWeightViews))
		);
		b.append(String.format("mLayout = %s\n", String.valueOf(mLayout)));

		return b.toString();
	}

	void setDebug (boolean debug) {
		if (debug) {
			setBorder(new Border(new BorderStroke(
				Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4)
			)));
		} else {
			setBorder(null);
		}
	}
}
