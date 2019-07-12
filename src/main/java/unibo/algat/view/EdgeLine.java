package unibo.algat.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
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
public class EdgeLine extends Path {
	private EdgeWeight<?> mWeight;

	private final ObjectBinding<Point2D> mStart, mEnd;
	private final ObjectBinding<Point2D> mTop;
	private final ObjectBinding<QuadCurveTo> mArc;

	private final ObjectBinding<Point2D> mHeadLeft, mHeadRight;

	/**
	 * @param u First edge node
	 * @param v Second edge node
	 * @param weight Weigth associated to the u, v nodes. Can be {@code null}.
	 */
	public EdgeLine(NodeView u, NodeView v, EdgeWeight<?> weight) {
		setWeight(weight);
        // TODO Set stroke Paint

		mTop  = new ObjectBinding<>() {
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
	}

	public void setWeight(EdgeWeight<?> weight) {
		strokeWidthProperty().unbind();

		mWeight = weight;

		if (mWeight != null) {
			strokeWidthProperty().bind(
                new DoubleBinding() {
					{
						bind(mWeight);
					}

					@Override
					protected double computeValue() {
						return 1 + Math.pow(
							Math.abs(mWeight.get()), 1 / 4.0
						);
					}
				}
			);
		}
	}

	public EdgeWeight<?> getWeight () {
		return mWeight;
	}
}
