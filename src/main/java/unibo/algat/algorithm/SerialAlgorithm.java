package unibo.algat.algorithm;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ResourceBundle;

public abstract class SerialAlgorithm<R> extends Task<R> {
	private Integer mCounter = 0;
	private final Object mCounterLock = new Object();

	private BooleanProperty mStopped;
	protected final ResourceBundle mInterface;

	public SerialAlgorithm () {
		mStopped = new SimpleBooleanProperty(this, "stop", false);
		mInterface = ResourceBundle.getBundle("Interface");

		// TODO Ensure threads don't interfere with the mStopped property
		mStopped.bind(new StoppedBinding());
	}

	protected final void setBreakpoint () throws InterruptedException {
		synchronized (mCounterLock) {
			while (mCounter == 0) {
				mCounterLock.wait();
			}

			mCounter = Math.max(0, mCounter - 1);
		}
	}

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

	public boolean isStopped () {
		return mStopped.get();
	}

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
