package unibo.algat.view;

import javafx.geometry.Point2D;

public interface GraphLayout {
	/**
	 * @return A {@link Point2D} object, indicating the coordinates within
	 * the {@link GraphView} at which position {@code node}.
	 */
	Point2D layout (NodeView node);

	/**
	 * @param view {@link NodeView} to query the position for.
	 * @return The cell position this view was associated to.
	 * @throws IllegalArgumentException if {@code view} hasn't yet been laid
	 * out.
	 */
	int getPosition(NodeView view);

	/**
	 * Removes the given {@link NodeView} from the layout, recycling its
	 * position for a later node view.
	 * @param view {@link NodeView} object to remove from this layout.
	 */
	void remove (NodeView view);

	/**
	 * Clears all associations regarding {@link NodeView} positions within a
	 * {@link GraphView}.
	 */
	void clear();
}
