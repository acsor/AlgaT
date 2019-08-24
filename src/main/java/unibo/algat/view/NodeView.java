package unibo.algat.view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import unibo.algat.graph.Node;
import unibo.algat.util.DragFactory;

class NodeView extends Region {
	private Node<?> mNode;

	private final Circle mCircle;
	private final Text mText;

	private SimpleDoubleProperty mRadius;
	private SimpleObjectProperty<Point2D> mCenter;

	private static final PseudoClass PSEUDO_CLASS_SELECTED =
		PseudoClass.getPseudoClass("selected");
	private BooleanProperty mSelected = new SimpleBooleanProperty(
		this, "selected"
	) {
		@Override
		protected void invalidated () {
			pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, get());
		}
	};

	private final EventHandler<MouseEvent> mToggleSelected = e -> {
		if (e.isControlDown())
			mSelected.set(!mSelected.get());
	};

	private static final double DEFAULT_RADIUS = 4.0;
	private static final Paint DEFAULT_FILL = Color.rgb(62, 134, 160);

	NodeView(Node<?> node) {
		mNode = node;
		mCircle = new Circle(DEFAULT_RADIUS, DEFAULT_FILL);
		mText = new Text();

		mRadius = new SimpleDoubleProperty();
		mCenter = new SimpleObjectProperty<>();

		getStyleClass().add("node-view");
		mCircle.getStyleClass().add("node-view-circle");
		mText.getStyleClass().add("node-view-text");

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

		mCircle.setOnMouseClicked(mToggleSelected);
		mText.setOnMouseClicked(mToggleSelected);
		DragFactory.makeDraggable(this, mCircle, mText);

		getChildren().addAll(mCircle, mText);
	}

	public Node<?> getNode () {
		return mNode;
	}

	Point2D getCenter () {
		return mCenter.get();
	}

	ReadOnlyObjectProperty<Point2D> centerProperty () {
		return mCenter;
	}

	void setSelected (boolean selected) {
		mSelected.set(selected);
	}

	public boolean isSelected () {
		return mSelected.get();
	}

	public BooleanProperty selectedProperty () {
		return mSelected;
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

	void setRadius (double radius) {
		mRadius.set(radius);
	}

	double getRadius () {
        return mRadius.get();
	}

	DoubleProperty radiusProperty () {
		return mRadius;
	}

	void setFill (Paint paint){
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

	public Paint getOutline () {
		return mCircle.getStroke();
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
