package unibo.algat.view;

import javafx.scene.shape.Line;

public class ArcView extends Line {
	public ArcView () {
		super();

		setStrokeWidth(2.5);
	}

	public void orient (NodeView u, NodeView v) {
		final double radius = u.getRadius();

		setStartX(u.getLayoutX() + radius);
		setStartY(u.getLayoutY() + radius);
		setEndX(v.getLayoutX() + radius);
		setEndY(v.getLayoutY() + radius);
	}
}
