package unibo.algat.view;

import javafx.animation.FadeTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

/**
 * <p>A class responsible for tracing a parabola curve between two
 * {@link NodeView}s.</p>
 */
class EdgeView extends Path {
	private NodeView mU, mV;
	private final FadeTransition mFadeIn;

	private final ObjectBinding<Point2D> mStart, mEnd;
	/**
	 * The only Bézier control point of this quadratic curve.
	 */
	private final ObjectBinding<Point2D> mControl;
	private final ObjectBinding<QuadCurveTo> mArc;
	/**
	 * <p>The top point of this quadratic curve. This is calculated directly by
	 * Bézier formula, not JavaFX APIs, for the narrow case of {@code t = 0.5}.
	 * </p>
	 *
	 * @see
	 * <a href="http://www.idav.ucdavis.edu/education/CAGDNotes/Quadratic-Bezier-Curves.pdf">
	 * This research article</a> detailing the Bézier curve construction.
	 */
	private final ObjectProperty<Point2D> mTop = new SimpleObjectProperty<>(
		this, "top"
	);
	// The angle formed by the start and end graph nodes
	private DoubleProperty mAngle;

	private final ObjectBinding<Point2D> mHeadLeft, mHeadRight;

	private static final Duration FADE_DURATION = Duration.millis(2000);
	private static final double FADE_START = 0.4;

	/**
	 * @param u First edge node
	 * @param v Second edge node
	 */
	EdgeView(NodeView u, NodeView v) {
		mU = u;
		mV = v;
		mFadeIn = new FadeTransition(FADE_DURATION, this);

		mControl = new ObjectBinding<>() {
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
		};

		mStart = new ObjectBinding<>() {
			{ bind(u.centerProperty(), u.radiusProperty(), mControl); }

			@Override
			protected Point2D computeValue() {
				Point2D diff =
					mControl.get().subtract(u.getCenter()).normalize();
				// TODO Compute the start and end points as the intersection
				//  between the parabola and the circumferences
				return u.getCenter().add(diff.multiply(u.getRadius()));
			}
		};
		mEnd = new ObjectBinding<>() {
			{ bind(v.centerProperty(), v.radiusProperty(), mControl); }

			@Override
			protected Point2D computeValue() {
                Point2D diff = mControl.get().subtract(v.getCenter()).normalize();

				return v.getCenter().add(diff.multiply(v.getRadius()));
			}
		};
		mArc = new ObjectBinding<>() {
			{ bind(mStart, mControl, mEnd); }

			@Override
			protected QuadCurveTo computeValue () {
                return new QuadCurveTo(
                    mControl.get().getX(), mControl.get().getY(),
					mEnd.get().getX(), mEnd.get().getY()
				);
			}
		};
		mTop.bind(
			new ObjectBinding<>() {
				{ bind(mStart, mControl, mEnd); }

				@Override
				protected Point2D computeValue() {
					// NOTE: readers willing to take advantage of the
					// generalized Bézier formula can do so by inspecting an
					// earlier version of the source code (more likely revision
					// c40b8e1a5a684b3fee1c1d459b46a9bd6d26469a). The version
					// below is optimized for the narrow case of 't = 0.5'.
					final Point2D start = mStart.get();
					final Point2D c = mControl.get();
					final Point2D end = mEnd.get();

					return new Point2D(
						.25 * start.getX() + .5 * c.getX() + .25 * end.getX(),
						.25 * start.getY() + .5 * c.getY() + .25 * end.getY()
					);
				}
			}
		);
		mHeadLeft = new ObjectBinding<>() {
			{ bind(mControl, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mControl.get().subtract(mEnd.get()).normalize();
				// Get diff angle and add 30 degrees to it
				final double newAngle = Math.atan2(
					diff.getY(), diff.getX()
				) + Math.PI / 6.0;

				diff = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

				return mEnd.get().add(diff.multiply(15));
			}
		};
		mHeadRight = new ObjectBinding<>() {
			{ bind(mControl, mEnd); }

			@Override
			protected Point2D computeValue() {
				Point2D diff = mControl.get().subtract(mEnd.get()).normalize();
				// Get diff angle and subtract 30 degrees to it
				final double newAngle = Math.atan2(
					diff.getY(), diff.getX()
				) - Math.PI / 6.0;

				diff = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

				return mEnd.get().add(diff.multiply(15));
			}
		};
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

        mFadeIn.setFromValue(FADE_START);

		getStyleClass().add("edge-view");
	}

	ReadOnlyDoubleProperty angleProperty () {
		if (mAngle == null) {
			mAngle = new SimpleDoubleProperty(this, "angle");

			mAngle.bind(new DoubleBinding() {
				{ bind(mU.centerProperty(), mV.centerProperty()); }

				@Override
				protected double computeValue() {
					final Point2D start = mU.getCenter(), end = mV.getCenter();

					return Math.atan2(
						end.getY() - start.getY(), end.getX() - start.getX()
					);
				}
			});
		}

		return mAngle;
	}

	Point2D getTop () {
		return mTop.get();
	}


	/**
	 * Begins the execution of a fade in animation.
	 */
	void fadeIn () {
		// The target opacity level is not statically set, but changes
		// according to the current level during invocation time
		mFadeIn.setToValue(getOpacity());
		mFadeIn.play();
	}

	/**
	 * @return The top point described by this parabola curve.
	 */
	ReadOnlyObjectProperty<Point2D> topProperty () {
		return mTop;
	}
}
