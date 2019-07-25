package unibo.algat.view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import unibo.algat.graph.Node;

public class NodeView extends Region {
	private Node<?> mNode;

	private Circle mCircle;
	private Label mId;
	private Label mLabel;

	private SimpleDoubleProperty mRadius;
	private SimpleObjectProperty<Point2D> mCenter;
	private Point2D mDragStart;

	private final EventHandler<MouseEvent> mMousePressed = event -> {
		mDragStart = new Point2D(event.getX(), event.getY());
		setOpacity(getOpacity() - 0.5);
	};
	private final EventHandler<MouseEvent> mMouseDrag = event -> {
		Point2D diff = new Point2D(event.getX(), event.getY()).subtract(
			mDragStart
		);

		setTranslateX(getTranslateX() + diff.getX());
		setTranslateY(getTranslateY() + diff.getY());
	};
	private final EventHandler<MouseEvent> mMouseReleased = event -> {
		setOpacity(getOpacity() + 0.5);
	};

	private static final double DEFAULT_RADIUS = 4.0;
	private static final Paint DEFAULT_FILL = Color.rgb(62, 134, 160);

	public NodeView(Node<?> node) {
		mNode = node;
		mCircle = new Circle(DEFAULT_RADIUS, DEFAULT_FILL);
		mId = new Label(String.valueOf(mNode.getId()));
		mLabel = new Label();

		mRadius = new SimpleDoubleProperty();
		mCenter = new SimpleObjectProperty<>();

		getStyleClass().add("node-view");
		// TODO Why I cannot apply the .node-view > .circle selector is out
		//  of me
		mCircle.getStyleClass().add("node-view-circle");
		mId.getStyleClass().add("node-view-id");

		mLabel.textProperty().bind(mNode.dataProperty().asString());
		mCircle.setStrokeType(StrokeType.INSIDE);
		mRadius.bindBidirectional(mCircle.radiusProperty());
		mCircle.strokeWidthProperty().bind(mRadius.multiply(0.1));

		mCenter.bind(new ObjectBinding<>() {
			{
				bind(
					layoutXProperty(), layoutYProperty(), translateXProperty(),
					translateYProperty(), mRadius
				);
			}

			@Override
			protected Point2D computeValue() {
				final Insets bounds = getInsets();

				return new Point2D(
					getLayoutX() + getTranslateX() + mRadius.get() + bounds.getLeft(),
					getLayoutY() + getTranslateY() + mRadius.get() + bounds.getTop()
				);
			}
		});

		// TODO Allow NodeViews to only display one thing -- provide a text
		//  property and let users of the class choose what to show
		//  (particularly, either an id or a "weight" value
		mCircle.setOnMousePressed(mMousePressed);
		mCircle.setOnMouseDragged(mMouseDrag);
		mCircle.setOnMouseReleased(mMouseReleased);
		mId.setOnMousePressed(mMousePressed);
		mId.setOnMouseDragged(mMouseDrag);
		mId.setOnMouseReleased(mMouseReleased);

		getChildren().addAll(mCircle, mId, mLabel);
	}

	public Point2D getCenter () {
		return mCenter.get();
	}

	public SimpleObjectProperty<Point2D> centerProperty () {
		return mCenter;
	}

	public void setRadius (double radius) {
		mRadius.set(radius);
	}

	public double getRadius () {
        return mRadius.get();
	}

	public DoubleProperty radiusProperty () {
		return mRadius;
	}

	public void setFill (Paint paint){
		mCircle.setFill(paint);

		if (paint instanceof Color)
			mCircle.setStroke(((Color) paint).darker());
	}

	public Paint getFill () {
		return mCircle.getFill();
	}

	public ObjectProperty<Paint> fillProperty () {
		return mCircle.fillProperty();
	}

	public ObjectProperty<Paint> outlineProperty () {
		return mCircle.strokeProperty();
	}

	@Override
	protected void layoutChildren () {
		final Insets bounds = getInsets();
		final double x = bounds.getLeft(), y = bounds.getTop(),
			width = getWidth() - bounds.getLeft() - bounds.getRight(),
			height = getHeight() - bounds.getTop() - bounds.getBottom();

		layoutInArea(mCircle, x, y, width, height, 0, HPos.CENTER, VPos.TOP);
		layoutInArea(
			mId, x, y, width, 2 * mRadius.get() , 0, HPos.CENTER, VPos.CENTER
		);
		layoutInArea(mLabel, x, y, width, height, 0, HPos.CENTER, VPos.BOTTOM);
	}

	@Override
	protected double computePrefWidth (double height) {
		final Insets insets = getInsets();

		return insets.getLeft() + 2 * mRadius.get() + insets.getRight();
	}

	@Override
	protected double computePrefHeight (double width) {
		final Insets insets = getInsets();

		return insets.getTop() + 2 * mRadius.get() + insets.getBottom()
			+ mLabel.getHeight();
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
