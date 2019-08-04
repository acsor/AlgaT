package unibo.algat.util;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * <p>Utility class to turn a {@link Node} object into a draggable entity.</p>
 */
public class DragFactory {
	private static final double OPACITY_SHIFT = 0.5;

	/**
	 * <p>Makes {@code target} a draggable node when drag events are detected on
	 * {@code triggers}.</p><br>
	 *
	 * <p>Specifying a list of "triggers" is useful because, for example,
	 * certain {@code Node}s might contain some padding around them, making a
	 * drag around their empty space feel unnatural; or others would like to
	 * expose an handle to perform the drag.</p>
	 *
	 * <p>If one wishes to effectively start a drag only when some subnodes are
	 * clicked, these have to be specified in the {@code triggers} parameter.
	 * Note however that there's no restriction whatsoever on what nodes can
	 * trigger the event.</p>
	 * <p>If on the other hand, a given {@code node} is to be the trigger of
	 * its own movement, then the proper invocation is
	 * {@code makeDraggable(node, node)}.</p>
	 *
	 * @param target Node which is to be moved around
	 * @param triggers Nodes triggering a drag event
	 */
	public static void makeDraggable (Node target, Node ... triggers) {
		final DragContext drag = new DragContext();

		for (Node trigger: triggers) {
			trigger.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
				drag.start = new Point2D(event.getX(), event.getY());
				target.setOpacity(target.getOpacity() - OPACITY_SHIFT);

				event.consume();
			});
			trigger.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
				drag.diff = new Point2D(event.getX(), event.getY()).subtract(
					drag.start
				);

				target.setTranslateX(target.getTranslateX() + drag.diff.getX());
				target.setTranslateY(target.getTranslateY() + drag.diff.getY());

				event.consume();
			});
			trigger.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
				target.setOpacity(target.getOpacity() + OPACITY_SHIFT);
				event.consume();
			});
		}
	}

	private static class DragContext {
		Point2D start, diff;

		DragContext () {
			start = new Point2D(0, 0);
			diff = new Point2D(0, 0);
		}
	}
}
