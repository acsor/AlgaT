package unibo.algat.view;

/**
 * <p>Interface for GUI components wishing to gain access to the singleton
 * {@link AlgaToolBar}.</p>
 */
public interface ToolBarUser {
	/**
	 * Called when the tool bar user is granted access to the tool bar. The
	 * provided callback is the place where set button actions or disable any
	 * one of them.
	 */
	void onAcquireToolBar(AlgaToolBar toolBar);
}
