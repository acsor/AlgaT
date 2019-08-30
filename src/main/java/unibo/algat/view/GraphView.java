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
 * <p>A view class responsible of displaying a {@link Graph} object.</p>
 */
public class GraphView<T> extends Region {
	private ObjectProperty<ObservableGraph<T>> mGraph;
	private WeightFunction<T> mWeights;

	Map<Vertex<T>, VertexView> mVertices;
	private Map<Pair<Vertex<T>, Vertex<T>>, EdgeView> mEdges;
	private Map<Pair<Vertex<T>, Vertex<T>>, WeightView> mWeightViews;
	GraphLayout mLayout;
	GraphVertexSelectionModel mVertexSelection;

	private double mVertexRadius, mVertexMargin;
	// Width and height values to return in computePrefWidth() and
	// computePrefHeight()
	private double mPrefWidth, mPrefHeight;
	private Paint mVertexFill;
	private Function<Vertex<T>, StringBinding> mVertexFormatter;

	private static final double DEFAULT_VERTEX_RADIUS = 1;
	private static final double DEFAULT_VERTEX_MARGIN = 1;

	private static final double VERTEX_VIEW_ORDER = 1;
	private static final double WEIGHT_LABEL_VIEW_ORDER = 2;
	private static final double EDGE_VIEW_ORDER = 3;

	private final VertexChangeListener<T> mVertexListener = e -> {
		if (e.wasInserted())
			addVertexView(e.getVertex());
		else if (e.wasDeleted())
			removeVertexView(e.getVertex());
	};
	private final EdgeChangeListener<T> mEdgeListener = e -> {
		if (e.wasInserted())
			addEdgeView(e.getFirst(), e.getSecond());
		else if (e.wasDeleted())
			removeEdgeView(e.getFirst(), e.getSecond());
	};

	/**
	 * A vertex formatter associating a {@link Vertex} to its id.
	 *
	 * @see #setVertexFormatter
	 */
	public final Function<Vertex<T>, StringBinding> VERTEX_ID_FORMATTER = v ->
		new StringBinding() {
			@Override
			protected String computeValue() {
				return String.valueOf(v.getId());
			}
		};

	public GraphView () {
		mGraph = new SimpleObjectProperty<>(this, "graph");
		mVertices = new HashMap<>();
		mEdges = new HashMap<>();
		mWeightViews = new HashMap<>();

		mLayout = new GraphGridLayout(6);
		mVertexSelection = new GraphVertexSelectionModel(this);
		mVertexFormatter = VERTEX_ID_FORMATTER;

		mVertexRadius = DEFAULT_VERTEX_RADIUS;
		mVertexMargin = DEFAULT_VERTEX_MARGIN;
	}

	public void setGraph(Graph<T> graph) {
		if (mGraph.get() != null) {
			mGraph.get().removeVertexChangeListener(mVertexListener);
			mGraph.get().removeEdgeChangeListener(mEdgeListener);
		}

		mVertexSelection.clearSelection();
		mLayout.clear();
		mWeightViews.clear();
		setWeightFunction(null);
		mEdges.clear();
		mVertices.clear();
		getChildren().clear();

		if (graph != null) {
			mGraph.set(new ObservableGraph<>(graph));
			mGraph.get().addVertexChangeListener(mVertexListener);
			mGraph.get().addEdgeChangeListener(mEdgeListener);

			for (Vertex<T> u: mGraph.get().vertices())
				addVertexView(u);

			for (Vertex<T> u: mGraph.get().vertices()) {
				for (Vertex<T> v: mGraph.get().adjacents(u))
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

	public void setVertexRadius(double radius) {
		mVertexRadius = radius;

		for (VertexView v: mVertices.values())
			v.setRadius(radius);
	}

	public double getVertexRadius() {
		return mVertexRadius;
	}

	public void setVertexMargin(double margin) {
		mVertexMargin = margin;

		for (VertexView v: mVertices.values())
			v.setPadding(new Insets(margin));
	}

	public double getVertexMargin() {
		return mVertexMargin;
	}

	public void setVertexFill(Paint vertexFill) {
		mVertexFill = vertexFill;

		for (VertexView v: mVertices.values())
			v.setFill(vertexFill);
	}

	public Paint getVertexFill() {
		return mVertexFill;
	}

	/**
	 * Specifies a <i>vertex formatter</i>, that is a function taking in an
	 * instance of {@link Vertex} and producing a {@link StringBinding} serving
	 * as its textual representation within a {@link VertexView}.
	 */
	public void setVertexFormatter(Function<Vertex<T>, StringBinding> formatter) {
		mVertexFormatter = formatter;
	}

	/**
	 * @return The vertex formatter in current use by this {@code GraphView}
	 * object.
	 *
	 * @see #setVertexFormatter
	 */
	public Function<Vertex<T>, StringBinding> getVertexFormatter() {
		return mVertexFormatter;
	}

	public void setGraphLayout (GraphLayout layout) {
		mVertexSelection.clearSelection();

		mLayout = layout;
        requestLayout();
	}

	public GraphLayout getGraphLayout () {
		return mLayout;
	}

	/**
	 * Invokes a fade in animation on edge {@code (u, v)}.
	 */
	public void fadeEdge (Vertex<T> u, Vertex<T> v) {
		mEdges.get(new Pair<>(u, v)).fadeIn();
	}

	@Override
	protected void layoutChildren () {
		final Insets bounds = getInsets();

		mPrefWidth = mPrefHeight = 0;

		for (VertexView view: mVertices.values()) {
			final Point2D location = mLayout.layout(view);

			layoutInArea(
				view,
				location.getX() + bounds.getLeft(),
				location.getY() + bounds.getTop(),
				view.computePrefWidth(-1), view.computePrefHeight(-1),
				0, HPos.CENTER, VPos.CENTER
			);

			mPrefWidth = Math.max(
				mPrefWidth, bounds.getLeft() + bounds.getRight() +
					location.getX() + view.getWidth()
			);
			mPrefHeight = Math.max(
				mPrefHeight, bounds.getTop() + bounds.getBottom() +
					location.getY() + view.getHeight()
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

	@Override
	protected double computePrefWidth (double height) {
		return mPrefWidth;
	}

	@Override
	protected double computePrefHeight (double width) {
		return mPrefHeight;
	}

	private void addVertexView(Vertex<T> vertex) {
		final VertexView view = new VertexView(vertex);

		view.textProperty().bind(mVertexFormatter.apply(vertex));
		view.setRadius(mVertexRadius);
		view.setFill(mVertexFill);
		view.setPadding(new Insets(mVertexMargin));
		view.setViewOrder(VERTEX_VIEW_ORDER);

		// When a VertexView is selected by a click, add it to the selection
		// model
		view.selectedProperty().addListener((o, wasSelected, isSelected) -> {
			if (!wasSelected && isSelected)
				mVertexSelection.select(view);
			else if (wasSelected && !isSelected)
				mVertexSelection.clearSelection(view);
		});

        mVertices.put(vertex, view);
        getChildren().add(view);
	}

	private void removeVertexView(Vertex<T> vertex) {
		final VertexView removed = mVertices.remove(vertex);

		if (removed != null) {
			mVertexSelection.clearSelection(removed);
			mLayout.remove(removed);
			getChildren().remove(removed);
		}
	}

	private void addEdgeView(Vertex<T> u, Vertex<T> v) {
		final EdgeView edge = new EdgeView(mVertices.get(u), mVertices.get(v));
		final WeightView weightView = new WeightView(
			edge, mWeights != null ? mWeights.weightBinding(u, v): null
		);

		edge.setViewOrder(EDGE_VIEW_ORDER);
		weightView.setViewOrder(WEIGHT_LABEL_VIEW_ORDER);

		mEdges.put(new Pair<>(u, v), edge);
		mWeightViews.put(new Pair<>(u, v), weightView);

		getChildren().addAll(edge, weightView);
	}

	private void removeEdgeView(Vertex<T> u, Vertex<T> v) {
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
			mVertices == null ? 0: mVertices.keySet().size(),
			mEdges == null ? 0: mEdges.keySet().size()
		));

		b.append(String.format("getChildren() = %d\n", getChildren().size()));
        b.append(String.format("mVertices = %s\n", String.valueOf(mVertices)));
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
