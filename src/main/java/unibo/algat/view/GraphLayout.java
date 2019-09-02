package unibo.algat.view;

import javafx.geometry.Point2D;

interface GraphLayout {
	/**
	 * @return A {@link Point2D} object, indicating the coordinates within
	 * the {@link GraphView} at which position {@code vertex}.
	 */
	Point2D layout (VertexView vertex);

	/**
	 * @param view {@link VertexView} to query the position for.
	 * @return The cell position this view was associated to.
	 * @throws IllegalArgumentException if {@code view} hasn't yet been laid
	 * out.
	 */
	int getPosition(VertexView view);

	/**
	 * Removes the given {@link VertexView} from the layout, recycling its
	 * position for a later vertex view.
	 * @param view {@link VertexView} object to remove from this layout.
	 */
	void remove (VertexView view);

	/**
	 * Clears all associations regarding {@link VertexView} positions within a
	 * {@link GraphView}.
	 */
	void clear();
}
