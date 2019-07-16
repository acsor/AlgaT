package unibo.algat.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import unibo.algat.graph.EdgeWeight;

/**
 * <p>A class responsible for tracing a parabola curve between two
 * {@link NodeView}s. It can optionally receive an {@link EdgeWeight}, be
 * notified by it and set its stroke width accordingly.</p>
 */
public class EdgeView extends Path {
	private DoubleProperty mWeight;

	private final ObjectBinding<Point2D> mStart, mEnd;
	private final ObjectProperty<Point2D> mTop;
	private final ObjectBinding<QuadCurveTo> mArc;
	// The angle formed by the start and end graph nodes
	private final DoubleProperty mAngle;

	private final ObjectBinding<Point2D> mHeadLeft, mHeadRight;

	/**
	 * @param u First edge node
	 * @param v Second edge node
	 * @param weight Weigth associated to the u, v nodes. Can be {@code null}.
	 */
	public EdgeView(NodeView u, NodeView v, EdgeWeight<?> weight) {
		mWeight = new SimpleDoubleProperty(this, "weight", 0);

		mTop = new SimpleObjectProperty<>(this, "top");
		mTop.bind(
			new ObjectBinding<>() {
				{ bind(u.centerProperty(), v.centerProperty()); }

				@Override
				protected Point2D computeValue() {
					final Point2D c1 = u.getCenter(), c2 = v.getCenter();
					final Point2D mid = c1.midpoint(c2);
					Point2D diff = c2.subtract(c1).normalize();

					// Rotate diff by 90 degrees
					diff = new Point2D(-diff.getY(), diff.getX());

					return mid.subtract(diff.multiply(c1.distance(c2) / 4.0));
				}
			}
		);

		mStart = new ObjectBinding<>() {
			{ bind(u.centerProperty(), u.radiusProperty(), mTop); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mTop.get().subtract(u.getCenter()).normalize();
				// TODO Compute the start and end points as the intersection
				//  between the parabola and the circumferences
				return u.getCenter().add(diff.multiply(u.getRadius()));
			}
		};
		mEnd = new ObjectBinding<>() {
			{ bind(v.centerProperty(), v.radiusProperty(), mTop); }

			@Override
			protected Point2D computeValue() {
                Point2D diff = mTop.get().subtract(v.getCenter()).normalize();

				return v.getCenter().add(diff.multiply(v.getRadius()));
			}
		};
		mArc = new ObjectBinding<>() {
			{ bind(mStart, mTop, mEnd); }

			@Override
			protected QuadCurveTo computeValue () {
                return new QuadCurveTo(
                    mTop.get().getX(), mTop.get().getY(),
					mEnd.get().getX(), mEnd.get().getY()
				);
			}
		};
		mAngle = new SimpleDoubleProperty(this, "angle");
		mHeadLeft = new ObjectBinding<>() {
			{ bind(mTop, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mTop.get().subtract(mEnd.get()).normalize();
				// Get diff angle and add 30 degrees to it
				final double newAngle = Math.atan2(
					diff.getY(), diff.getX()
				) + Math.PI / 6.0;

				diff = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

				return mEnd.get().add(diff.multiply(15));
			}
		};
		mHeadRight = new ObjectBinding<>() {
			{ bind(mTop, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mTop.get().subtract(mEnd.get()).normalize();
				// Get diff angle and subtract 30 degrees to it
				final double newAngle = Math.atan2(
					diff.getY(), diff.getX()
				) - Math.PI / 6.0;

				diff = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

				return mEnd.get().add(diff.multiply(15));
			}
		};

		mAngle.bind(new DoubleBinding() {
			{ bind(u.centerProperty(), v.centerProperty()); }

			@Override
			protected double computeValue() {
				final Point2D start = u.getCenter(), end = v.getCenter();

				return Math.atan2(
					end.getY() - start.getY(), end.getX() - start.getX()
				);
			}
		});
        mArc.addListener((observable, oldValue, newValue) -> {
			getElements().clear();
			getElements().addAll(
				new MoveTo(mStart.get().getX(), mStart.get().getY()),
				mArc.get(),
				new MoveTo(mHeadLeft.get().getX(), mHeadLeft.get().getY()),
				new LineTo(mEnd.get().getX(), mEnd.get().getY()),
				new MoveTo(mEnd.get().getX(), mEnd.get().getY()),
				new LineTo(mHeadRight.get().getX(), mHeadRight.get().getY())
			);
		});

        // Make the curve width proportional to 1 + w ^ 1/4
		strokeWidthProperty().bind(
			new DoubleBinding() {
				{ bind(mWeight); }

				@Override
				protected double computeValue() {
					return 1 + Math.pow(Math.abs(mWeight.get()), 1 / 4.0);
				}
			}
		);

		setWeight(weight);
		// TODO Choose nicer stroke
		setStroke(Color.web("#353E4C"));
		setStrokeLineCap(StrokeLineCap.ROUND);
		setStrokeLineJoin(StrokeLineJoin.ROUND);
	}

	public ObjectProperty<Point2D> topProperty () {
		return mTop;
	}

	public DoubleProperty angleProperty () {
		return mAngle;
	}

	public void setWeight(EdgeWeight<?> weight) {
		if (weight != null)
			mWeight.bind(weight);
	}

	public Double getWeight () {
		return mWeight.get();
	}

	public DoubleProperty weightProperty() {
		return mWeight;
	}
}
