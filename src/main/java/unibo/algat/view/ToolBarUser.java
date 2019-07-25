package unibo.algat.view;

/**
 * <p>Interface for GUI components wishing to gain access to the singleton
 * {@link AlgaToolBar}.</p>
 */
public interface ToolBarUser {
	/**
	 * Invoked when the tool bar user is granted access to the tool bar. The
	 * provided callback is the place where set button actions or disable any
	 * one of them.
	 */
	void onAcquireToolBar(AlgaToolBar toolBar);

	/**
	 * Invoked when the tool bar user leaves control of the tool bar.
	 * Code in this method should undo actions performed in
	 * {@link #onAcquireToolBar} taking into account however that:
	 * <ul>
	 *     <li>{@code onAction} callbacks</li>
	 *     <li>disable properties</li>
	 * </ul>
	 *
	 * are automatically reset by the tool bar. There is no need to manually
	 * undo them.
	 */
	void onReleaseToolBar(AlgaToolBar toolBar);
}
