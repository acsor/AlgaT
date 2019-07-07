package unibo.algat.view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;

public class ArcView extends Path {
	private SimpleObjectProperty<Point2D> mStartCenter, mEndCenter;
	private DoubleProperty mStartRadius, mEndRadius;
	private SimpleObjectProperty<MoveTo> mStart;
	private SimpleObjectProperty<CubicCurveTo> mArc;

	private ChangeListener<PathElement> mPathUpdater = new ChangeListener<>() {
		@Override
		public void changed(
			ObservableValue<? extends PathElement> observable,
			PathElement oldValue, PathElement newValue
		) {
            getElements().clear();
            getElements().addAll(mStart.get(), mArc.get());
		}
	};

	public ArcView (NodeView u, NodeView v) {
		mStartCenter = new SimpleObjectProperty<>();
		mEndCenter = new SimpleObjectProperty<>();
		mStartRadius = new SimpleDoubleProperty(u.getRadius());
		mEndRadius = new SimpleDoubleProperty(v.getRadius());
		mStart = new SimpleObjectProperty<>();
		mArc = new SimpleObjectProperty<>();

		setNodes(u, v);
		mStart.bind(new ObjectBinding<>() {
			{
				super.bind(mStartRadius, mStartCenter, mEndCenter);
			}

			@Override
			protected MoveTo computeValue() {
				Point2D diff =
					mEndCenter.get().subtract(mStartCenter.get()).normalize();
				Point2D start = mStartCenter.get().add(diff.multiply(
					mEndRadius.get())
				);

				return new MoveTo(start.getX(), start.getY());
			}
		});
		mArc.bind(new ObjectBinding<>() {
			{
                super.bind(mStartCenter, mEndCenter, mStartRadius, mEndRadius);
			}

			@Override
			protected CubicCurveTo computeValue() {
				Point2D diff =
					mEndCenter.get().subtract(mStartCenter.get()).normalize();
				Point2D start = mStartCenter.get().add(
					diff.multiply(mStartRadius.get())
				);
				Point2D end = mEndCenter.get().subtract(
					diff.multiply(mEndRadius.get())
				);
				Point2D rot = new Point2D(diff.getY(), diff.getX()).multiply(
					start.distance(end) / 4.0
				);
				CubicCurveTo arc = new CubicCurveTo();

				arc.setControlX1(start.midpoint(end).getX() + rot.getX());
				arc.setControlY1(start.midpoint(end).getY() + rot.getY());
				arc.setControlX2(start.midpoint(end).getX() + rot.getX());
				arc.setControlY2(start.midpoint(end).getY() + rot.getY());
				arc.setX(end.getX());
				arc.setY(end.getY());

				return arc;
			}
		});

        mStart.addListener(mPathUpdater);
		mArc.addListener(mPathUpdater);

		setStrokeWidth(2.5);

	}

	public void setNodes(NodeView u, NodeView v) {
		// TODO Do these unbind() calls do what I want, i.e. remove previous
		//  bindings?
		mStartCenter.unbind();
		mEndCenter.unbind();
		mStartRadius.unbind();
		mEndRadius.unbind();

		mStartCenter.bind(u.centerProperty());
		mEndCenter.bind(v.centerProperty());
		mStartRadius.bind(u.radiusProperty());
		mEndRadius.bind(v.radiusProperty());
	}
}
