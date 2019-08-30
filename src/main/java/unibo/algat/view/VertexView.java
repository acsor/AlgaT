package unibo.algat.view;

import javafx.animation.StrokeTransition;
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
import javafx.util.Duration;
import unibo.algat.graph.Vertex;
import unibo.algat.util.DragFactory;

class VertexView extends Region {
	private Vertex<?> mVertex;

	private final Circle mCircle;
	private final Text mText;
	private final StrokeTransition mStrokeFade;

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

	private static final double FADE_DURATION = 1500;
	private static final Color FADE_START = Color.rgb(255, 171, 38, 0.9);
	private static final Color FADE_END = Color.rgb(255, 255, 255, 0);

	VertexView(Vertex<?> vertex) {
		mVertex = vertex;
		mCircle = new Circle(DEFAULT_RADIUS, DEFAULT_FILL);
		mText = new Text();
		mStrokeFade = new StrokeTransition(
			Duration.millis(FADE_DURATION), mCircle, FADE_START, FADE_END
		);

		mRadius = new SimpleDoubleProperty();
		mCenter = new SimpleObjectProperty<>();

		getStyleClass().add("vertex-view");
		mCircle.getStyleClass().add("vertex-view-circle");
		mText.getStyleClass().add("vertex-view-text");

		mRadius.bindBidirectional(mCircle.radiusProperty());
		mCircle.strokeWidthProperty().bind(mRadius.multiply(0.15));
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
		mVertex.dataProperty().addListener(o -> mStrokeFade.play());
		DragFactory.makeDraggable(this, mCircle, mText);

		getChildren().addAll(mCircle, mText);
	}

	Vertex<?> getVertex() {
		return mVertex;
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

	boolean isSelected () {
		return mSelected.get();
	}

	BooleanProperty selectedProperty() {
		return mSelected;
	}

	void setText (String text) {
		mText.setText(text);
	}

	String getText () {
		return mText.getText();
	}

	StringProperty textProperty () {
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

	Paint getFill () {
		return mCircle.getFill();
	}

	ObjectProperty<Paint> fillProperty () {
		return mCircle.fillProperty();
	}

	void setOutline (Paint outline) {
		mCircle.setStroke(outline);
	}

	Paint getOutline () {
		return mCircle.getStroke();
	}

	ObjectProperty<Paint> outlineProperty () {
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
