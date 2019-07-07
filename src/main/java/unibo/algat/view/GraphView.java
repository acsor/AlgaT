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
 * <p>TODO Write documentation</p>
 * <p>TODO Improve the generics system, possibly getting rid of the type
 * parameter {@code T}.</p>
 * @param <T>
 */
public class GraphView<T> extends Region {
	private ObservableGraph<T> mGraph;

	private Map<Node<T>, NodeView> mNodes;
	private Map<Pair<Node<T>, Node<T>>, ArcView> mArcs;
	private GraphLayout mLayout;
	private double mNodeRadius, mNodeMargin;
	private Paint mNodeFill;

	private static final double DEFAULT_NODE_RADIUS = 1;
	private static final double DEFAULT_NODE_MARGIN = 1;

	private NodeChangeListener<T> mNodeListener = e -> {
		if (e.wasInserted()) {
			addNodeView(e.getNode());
		} else if (e.wasDeleted()) {
			removeNodeView(e.getNode());
		}
	};
	private EdgeChangeListener<T> mEdgeListener = e -> {
		if (e.wasInserted()) {
			addArcView(e.getFirst(), e.getSecond());
		} else if (e.wasDeleted()) {
			removeArcView(e.getFirst(), e.getSecond());
		}
	};

	public GraphView () {
		mNodes = new HashMap<>();
		mArcs = new HashMap<>();
		mLayout = new GraphGridLayout(6);
		mNodeRadius = DEFAULT_NODE_RADIUS;
		mNodeMargin = DEFAULT_NODE_MARGIN;
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
		mArcs = new HashMap<>();

		getChildren().remove(0, getChildren().size());

        for (Node<T> u: mGraph.nodes())
			addNodeView(u);

		for (Node<T> u: mGraph.nodes()) {
            for (Node<T> v: mGraph.adjacents(u))
            	addArcView(u, v);
		}
	}

	public void setGraph(GraphFactory<T> factory) {
		setGraph(factory.makeGraph());
	}

	public Graph<T> getGraph () {
		return mGraph;
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
			double width = Math.min(
				view.computePrefWidth(-1), getWidth() - bounds.getLeft() -
					bounds.getRight() - location.getX()
			);
			double height = Math.min(
				view.computePrefHeight(-1), getHeight() - bounds.getTop() -
					bounds.getBottom() - location.getY()
			);

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
				width, height , 0, HPos.CENTER, VPos.CENTER
			);
		}

		for (Pair<Node<T>, Node<T>> key: mArcs.keySet()) {
			mArcs.get(key).setNodes(
				mNodes.get(key.getFirst()), mNodes.get(key.getSecond())
			);
		}
	}

	private void addNodeView (Node<T> node) {
		final NodeView view = new NodeView(node);

		view.setRadius(mNodeRadius);
		view.setFill(mNodeFill);
		view.setPadding(new Insets(mNodeMargin));

        mNodes.put(node, view);
        getChildren().add(view);
	}

	private void removeNodeView (Node<T> node) {
		NodeView removed = mNodes.remove(node);

		if (removed != null) {
			getChildren().remove(removed);
		}
	}

	private void addArcView(Node<T> u, Node<T> v) {
		final ArcView view = new ArcView(mNodes.get(u), mNodes.get(v));

		mArcs.put(new Pair<>(u, v), view);
		getChildren().add(view);
	}

	private void removeArcView(Node<T> u, Node<T> v) {
		ArcView view = mArcs.remove(new Pair<>(u, v));

		if (view != null)
			getChildren().remove(view);
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
