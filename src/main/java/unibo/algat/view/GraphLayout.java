package unibo.algat.view;

import javafx.geometry.Point2D;

public interface GraphLayout {
	/**
	 * @return A {@link Point2D} object, indicating the coordinates within
	 * the {@link GraphView} at which position {@code node}.
	 */
	Point2D layout (NodeView node);

	/**
	 * Removes the given {@link NodeView} from the layout, recycling its
	 * position for a later node view.
	 * @param view {@link NodeView} object to remove from this layout.
	 */
	void remove (NodeView view);
}
