package unibo.algat.algorithm;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SerialExecutor {
	private SerialAlgorithm<?> mAlgorithm;
	private ScheduledExecutorService mExecutor;
	private ScheduledFuture<?> mAutoHandle;
	private long mAutoStep;

	private BooleanProperty mAutoRun;

	public SerialExecutor (SerialAlgorithm<?> algorithm, long autoStep) {
		mAlgorithm = algorithm;
		mExecutor = new ScheduledThreadPoolExecutor(2);
		mAutoStep = autoStep;

		mAutoRun = new SimpleBooleanProperty(this, "autoRunning", false);

		mAlgorithm.stoppedProperty().addListener(
			(observable, wasStopped, stopped) -> pauseRunAuto()
		);
	}

	private void runAuto () {
		if (mAutoHandle == null && !mAlgorithm.isStopped()) {
			mAutoHandle = mExecutor.scheduleWithFixedDelay(
				new AutoRunTask(), 0, mAutoStep, TimeUnit.MILLISECONDS
			);
		} else {
			throw new IllegalStateException(
				 getClass().getSimpleName() + " is already auto running"
			);
		}
	}

	private void pauseRunAuto() {
		if (mAutoHandle != null) {
			mAutoHandle.cancel(true);
			mAutoHandle = null;
		}
	}

	public void next () {
		Platform.runLater(() -> {
			// Such calls as mAlgorithm.getState() can only be issued from
			// the Java FX thread -- this particular instance took me
			// hours to debug (no exception was shown)
			if (mAlgorithm.getState() == Worker.State.READY) {
				mExecutor.execute(mAlgorithm);
			}
		});

		mAlgorithm.next();
	}

	public void setAutoRunning (boolean autoRunning) {
		if (autoRunning)
			runAuto();
		else
			pauseRunAuto();

		mAutoRun.set(autoRunning);
	}

	public boolean isAutoRunning () {
		return mAutoRun.get();
	}

	public ReadOnlyBooleanProperty autoRunningProperty () {
		return mAutoRun;
	}

	private class AutoRunTask implements Runnable {
		@Override
		public void run() {
			next();
		}
	}
}
