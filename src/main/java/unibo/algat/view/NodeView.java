package unibo.algat.view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import unibo.algat.graph.Node;
import unibo.algat.util.DragFactory;

public class NodeView extends Region {
	private Node<?> mNode;

	private final Circle mCircle;
	private final Label mText;

	private SimpleDoubleProperty mRadius;
	private SimpleObjectProperty<Point2D> mCenter;

	private static final double DEFAULT_RADIUS = 4.0;
	private static final Paint DEFAULT_FILL = Color.rgb(62, 134, 160);

	public NodeView(Node<?> node) {
		mNode = node;
		mCircle = new Circle(DEFAULT_RADIUS, DEFAULT_FILL);
		mText = new Label();

		mRadius = new SimpleDoubleProperty();
		mCenter = new SimpleObjectProperty<>();

		getStyleClass().add("node-view");
		// TODO Why I cannot apply the .node-view > .circle selector is out
		//  of me
		mCircle.getStyleClass().add("node-view-circle");
		mText.getStyleClass().add("node-view-text");

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
				final Insets b = getInsets();

				return new Point2D(
					getLayoutX() + getTranslateX() + mRadius.get() + b.getLeft(),
					getLayoutY() + getTranslateY() + mRadius.get() + b.getTop()
				);
			}
		});

		DragFactory.makeDraggable(this, mCircle, mText);

		getChildren().addAll(mCircle, mText);
	}

	public Point2D getCenter () {
		return mCenter.get();
	}

	public SimpleObjectProperty<Point2D> centerProperty () {
		return mCenter;
	}

	public void setText (String text) {
		mText.setText(text);
	}

	public String getText () {
		return mText.getText();
	}

	public StringProperty textProperty () {
		return mText.textProperty();
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

	public void setOutline (Paint outline) {
		mCircle.setStroke(outline);
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
			mText, x, y, width, 2 * mRadius.get() , 0, HPos.CENTER, VPos.CENTER
		);
	}

	@Override
	protected double computePrefWidth (double height) {
		final Insets insets = getInsets();

		return insets.getLeft() + 2 * mRadius.get() + insets.getRight();
	}

	@Override
	protected double computePrefHeight (double width) {
		final Insets insets = getInsets();

		return insets.getTop() + 2 * mRadius.get() + insets.getBottom();
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
