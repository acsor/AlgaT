package unibo.algat.view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import unibo.algat.graph.*;
import unibo.algat.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class GraphView<T> extends Region {
	private ObservableGraph<T> mGraph;

	private Map<Node<T>, NodeView<T>> mNodes;
	private Map<Pair<Node<T>, Node<T>>, ArcView> mArcs;
	private GraphLayout mLayout;
	private double mNodeRadius, mNodeMargin, mNodeOutline;
	private Paint mNodeFill;

	private NodeChangeListener<T> mNodeListener = new NodeChangeListener<> () {
		@Override
		public void nodeChanged(NodeChangeEvent<T> e) {
			if (e.wasInserted()) {
				addNodeView(e.getNode());
			} else if (e.wasDeleted()) {
				removeNodeView(e.getNode());
			}
		}
	};
	private EdgeChangeListener<T> mEdgeListener = new EdgeChangeListener<> () {
		@Override
		public void edgeChanged(EdgeChangeEvent<T> e) {
			if (e.wasInserted()) {
				addArcView(e.getFirst(), e.getSecond());
			} else if (e.wasDeleted()) {
				removeArcView(e.getFirst(), e.getSecond());
			}
		}
	};

	public GraphView () {
		mLayout = new GraphGridLayout(6);
	}

	@Override
	protected void layoutChildren () {
		if (mGraph != null) {
            for (NodeView view: mNodes.values())
                mLayout.layout(view, getInsets());

			for (Pair<Node<T>, Node<T>> k: mArcs.keySet()) {
				mArcs.get(k).orient(
					mNodes.get(k.getFirst()), mNodes.get(k.getSecond())
				);
			}
		}
	}

	@Override
	protected double computePrefWidth (double height) {
		// TODO Better implement
        return height;
	}

	@Override
	protected double computePrefHeight (double width) {
		// TODO Better implement
		return width;
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

        for (Node<T> u: mGraph.nodes()) {
			addNodeView(u);

			for (Node<T> v: mGraph.adjacents(u))
				addArcView(u, v);
		}
	}

	public Graph<T> getGraph () {
		return mGraph;
	}

	public void setNodeRadius(double radius) {
		mNodeRadius = radius;
	}

	public double getNodeRadius() {
		return mNodeRadius;
	}

	public void setNodeMargin (double margin) {
		mNodeMargin = margin;
	}

	public double getNodeMargin() {
		return mNodeMargin;
	}

	public void setNodeOutline (double outline) {
		mNodeOutline = outline;
	}

	public double getNodeOutline() {
		return mNodeOutline;
	}

	public void setNodeFill (Paint nodeFill) {
		mNodeFill = nodeFill;
	}

	public Paint getNodeFill () {
		return mNodeFill;
	}

	public void setGraphLayout (GraphLayout layout) {
		mLayout = layout;
	}

	public GraphLayout getGraphLayout () {
		return mLayout;
	}

	private void addNodeView (Node<T> node) {
		final NodeView view = new NodeView<>(this, node);

        mNodes.put(node, view);
        getChildren().add(view);
	}

	private void removeNodeView (Node<T> node) {
		NodeView view = mNodes.remove(node);

		if (view != null)
			getChildren().remove(view);
	}

	private void addArcView(Node<T> u, Node<T> v) {
		final ArcView view = new ArcView();

		mArcs.put(new Pair<>(u, v), view);
		getChildren().add(view);
	}

	private void removeArcView(Node<T> u, Node<T> v) {
		ArcView view = mArcs.remove(new Pair<>(u, v));

		if (view != null)
			getChildren().remove(view);
	}
}
