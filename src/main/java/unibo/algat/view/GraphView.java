package unibo.algat.view;

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

/**
 * <p>A view class responsible for displaying a {@link Graph} object.</p>
 */
public class GraphView<T> extends Region {
	private ObservableGraph<T> mGraph;
	private WeightFunction<T> mWeights;

	private Map<Node<T>, NodeView> mNodes;
	private Map<Pair<Node<T>, Node<T>>, EdgeView> mEdges;
	private Map<Pair<Node<T>, Node<T>>, WeightView> mWeightViews;
	private GraphLayout mLayout;
	private double mNodeRadius, mNodeMargin;
	private Paint mNodeFill;

	private static final double DEFAULT_NODE_RADIUS = 1;
	private static final double DEFAULT_NODE_MARGIN = 1;

	private static final double NODE_VIEW_ORDER = 1;
	private static final double WEIGHT_LABEL_VIEW_ORDER = 2;
	private static final double EDGE_VIEW_ORDER = 3;

	private NodeChangeListener<T> mNodeListener = e -> {
		if (e.wasInserted()) {
			addNodeView(e.getNode());
		} else if (e.wasDeleted()) {
			removeNodeView(e.getNode());
		}
	};
	private EdgeChangeListener<T> mEdgeListener = e -> {
		if (e.wasInserted()) {
			addEdgeView(e.getFirst(), e.getSecond());
		} else if (e.wasDeleted()) {
			removeEdgeView(e.getFirst(), e.getSecond());
		}
	};

	public GraphView () {
		mNodes = new HashMap<>();
		mEdges = new HashMap<>();
		mWeightViews = new HashMap<>();

		mLayout = new GraphGridLayout(6);
		mNodeRadius = DEFAULT_NODE_RADIUS;
		mNodeMargin = DEFAULT_NODE_MARGIN;
	}

	public void setGraph(GraphFactory<T> factory) {
		setGraph(factory.makeGraph());
	}

	public void setGraph(Graph<T> graph) {
		if (mGraph != null) {
			mGraph.removeNodeChangeListener(mNodeListener);
			mGraph.removeEdgeChangeListener(mEdgeListener);
		}

		mGraph = new ObservableGraph<>(graph);
		mGraph.addNodeChangeListener(mNodeListener);
		mGraph.addEdgeChangeListener(mEdgeListener);

		mNodes = new HashMap<>(graph.nodes().size());
		mEdges = new HashMap<>();

		getChildren().clear();

        for (Node<T> u: mGraph.nodes())
			addNodeView(u);

		for (Node<T> u: mGraph.nodes()) {
            for (Node<T> v: mGraph.adjacents(u))
            	addEdgeView(u, v);
		}
	}

	public Graph<T> getGraph () {
		return mGraph;
	}

	public void setWeightFunction(WeightFunction<T> w) {
        mWeights = w;

        if (mWeights != null) {
			for (Pair<Node<T>, Node<T>> edge: mEdges.keySet()) {
				mEdges.get(edge).setWeight(
                    mWeights.weight(edge.getFirst(), edge.getSecond())
				);
			}
		}
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

	public void setGraphLayout (GraphLayout layout) {
		mLayout = layout;

        requestLayout();
	}

	public GraphLayout getGraphLayout () {
		return mLayout;
	}

	@Override
	protected void layoutChildren () {
		final Insets bounds = getInsets();

		for (NodeView view: mNodes.values()) {
			Point2D location = mLayout.layout(view);

			// TODO Ensure this is correct
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
			Point2D location = view.getEdgeView().topProperty().get();

			// TODO Important: labels need to be aligned more tightly to the
			//  top point of the parabola
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

		view.setRadius(mNodeRadius);
		view.setFill(mNodeFill);
		view.setPadding(new Insets(mNodeMargin));
		view.setOpacity(0.95);
		view.setViewOrder(NODE_VIEW_ORDER);

        mNodes.put(node, view);
        getChildren().add(view);
	}

	private void removeNodeView (Node<T> node) {
		NodeView removed = mNodes.remove(node);

		if (removed != null) {
			mLayout.remove(removed);
			getChildren().remove(removed);
		}
	}

	private void addEdgeView(Node<T> u, Node<T> v) {
		final EdgeView edge = new EdgeView(
			mNodes.get(u), mNodes.get(v),
			mWeights != null ? mWeights.weight(u, v): null
		);
		final WeightView weightView = new WeightView(edge);

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
