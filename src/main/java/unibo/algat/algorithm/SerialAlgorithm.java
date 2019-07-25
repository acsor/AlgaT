package unibo.algat.algorithm;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ResourceBundle;

/**
 * <p>A {@code SerialAlgorithm} is an algorithm, typically executed on a
 * background thread, capable of pausing its execution at arbitrary points.
 * This behavior is achieved through {@link #setBreakpoint} calls, allowing
 * the execution of the algorithm body to suspend until a call (usually from
 * another thread) is done to {@link #next}.</p>
 * <p>In order to provide your own implementation of a {@code SerialAlgorithm},
 * it is necessary to override the {@link #call} method from {@link Task},
 * placing calls to {@link #setBreakpoint} at those places where you want to
 * halt the algorithm.</p>
 *
 * @param <R> This algorithm result value type
 *
 * @see #call
 * @see #setBreakpoint
 */
public abstract class SerialAlgorithm<R> extends Task<R> {
	private Integer mCounter = 0;
	private final Object mCounterLock = new Object();

	private BooleanProperty mStopped;
	protected final ResourceBundle mInterface;

	public SerialAlgorithm () {
		mStopped = new SimpleBooleanProperty(this, "stopped", false);
		mInterface = ResourceBundle.getBundle("Interface");

		// TODO Ensure threads don't interfere with the mStopped property
		mStopped.bind(new StoppedBinding());
	}

	/**
	 * <p>Invoked by inherited classes to describe a point in the algorithm
	 * execution cycle where to pause until a call to {@link #next} is issued
	 * by clients (typically {@link SerialExecutor} -- no other classes are
	 * supposed to deal with {@link #next}).</p>
	 * <p>Users of the algorithm are allowed to step it forward by issuing a
	 * call to {@code SerialExecutor.next}, although it is always possible to
	 * query its status by accessing {@link Task} methods.</p>
	 *
	 * @throws InterruptedException If an external thread issues an
	 * interruption to this worker thread. {@link InterruptedException}s
	 * should be handled gracefully, allowing a proper termination of the
	 * algorithm.
	 */
	protected final void setBreakpoint () throws InterruptedException {
		synchronized (mCounterLock) {
			while (mCounter == 0) {
				mCounterLock.wait();
			}

			mCounter = Math.max(0, mCounter - 1);
		}
	}

	/**
	 * <p>Augments the number of steps, awakening the worker thread of the
	 * change.</p>
	 * <p>This method is primarily supposed to be invoked by another thread
	 * other than the one where the algorithm is currently executed, to permit
	 * parallel execution.</p>
	 */
	final void next () {
		synchronized (mCounterLock) {
			mCounter = Math.max(0, mCounter + 1);
			mCounterLock.notifyAll();
		}
	}

	@Override
	protected void scheduled () {
		updateMessage(mInterface.getString("gui.algorithm.scheduled"));
	}

	@Override
	protected void running () {
		updateMessage(mInterface.getString("gui.algorithm.running"));
	}

	@Override
	protected void succeeded() {
		updateMessage(mInterface.getString("gui.algorithm.succeeded"));
	}

	@Override
	protected void cancelled () {
		updateMessage(mInterface.getString("gui.algorithm.cancelled"));
	}

	@Override
	protected void failed () {
		updateMessage(mInterface.getString("gui.algorithm.failed"));
	}

	/**
	 * @return The value of the {@code stopped} property.
	 */
	public boolean isStopped () {
		return mStopped.get();
	}

	/**
	 * @return A {@code BooleanProperty}, indicating whether this algorithm
	 * has stopped (due to success, failure or user abortion).
	 */
	public ReadOnlyBooleanProperty stoppedProperty () {
		return mStopped;
	}

	private class StoppedBinding extends BooleanBinding {
		StoppedBinding() {
			bind(stateProperty());
		}

		@Override
		protected boolean computeValue() {
			return getState().compareTo(Worker.State.SUCCEEDED) >= 0;
		}
	}
}
