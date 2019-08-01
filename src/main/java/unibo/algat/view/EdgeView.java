package unibo.algat.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import unibo.algat.graph.EdgeWeight;

/**
 * <p>A class responsible for tracing a parabola curve between two
 * {@link NodeView}s. It can optionally receive an {@link EdgeWeight}, be
 * notified by it and set its stroke width accordingly.</p>
 */
public class EdgeView extends Path {
	private final ObjectBinding<Point2D> mStart, mEnd;
	private final ObjectProperty<Point2D> mControlPoint;
	private final ObjectBinding<QuadCurveTo> mArc;
	private final ObjectBinding<Point2D> mMidPoint;
	// The angle formed by the runAuto and end graph nodes
	private final DoubleProperty mAngle;

	private final ObjectBinding<Point2D> mHeadLeft, mHeadRight;

	/**
	 * @param u First edge node
	 * @param v Second edge node
	 */
	public EdgeView(NodeView u, NodeView v) {
		mControlPoint = new SimpleObjectProperty<>(this, "top");
		mControlPoint.bind(
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
			{ bind(u.centerProperty(), u.radiusProperty(), mControlPoint); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mControlPoint.get().subtract(u.getCenter()).normalize();
				// TODO Compute the runAuto and end points as the intersection
				//  between the parabola and the circumferences
				return u.getCenter().add(diff.multiply(u.getRadius()));
			}
		};
		mEnd = new ObjectBinding<>() {
			{ bind(v.centerProperty(), v.radiusProperty(), mControlPoint); }

			@Override
			protected Point2D computeValue() {
                Point2D diff = mControlPoint.get().subtract(v.getCenter()).normalize();

				return v.getCenter().add(diff.multiply(v.getRadius()));
			}
		};
		mArc = new ObjectBinding<>() {
			{ bind(mStart, mControlPoint, mEnd); }

			@Override
			protected QuadCurveTo computeValue () {
                return new QuadCurveTo(
                    mControlPoint.get().getX(), mControlPoint.get().getY(),
					mEnd.get().getX(), mEnd.get().getY()
				);
			}
		};
		mMidPoint = new ObjectBinding<>() {
			{ bind(mStart, mControlPoint, mEnd);}

			@Override
			protected Point2D computeValue() {
				double t = 0.5;
				return new Point2D(
						(1-t) * (1-t) * mStart.get().getX() +
								2 * (1-t) * t * mControlPoint.get().getX() + t * t * mEnd.get().getX(),
						(1-t) * (1-t) * mStart.get().getY() +
								2 * (1-t) * t * mControlPoint.get().getY() + t * t * mEnd.get().getY()
				);
			}
		};
		mAngle = new SimpleDoubleProperty(this, "angle");
		mHeadLeft = new ObjectBinding<>() {
			{ bind(mControlPoint, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mControlPoint.get().subtract(mEnd.get()).normalize();
				// Get diff angle and add 30 degrees to it
				final double newAngle = Math.atan2(
					diff.getY(), diff.getX()
				) + Math.PI / 6.0;

				diff = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

				return mEnd.get().add(diff.multiply(15));
			}
		};
		mHeadRight = new ObjectBinding<>() {
			{ bind(mControlPoint, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mControlPoint.get().subtract(mEnd.get()).normalize();
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

        getStyleClass().add("edge-view");
	}

	public ObjectProperty<Point2D> topProperty () {
		return mControlPoint;
	}

	public DoubleProperty angleProperty () {
		return mAngle;
	}

	public Point2D getMidpoint() {
		return mMidPoint.get();
	}
}
