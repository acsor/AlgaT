package unibo.algat.algorithm;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker;

import java.util.concurrent.*;

/**
 * <p>Auxiliary class aiding the execution of a {@link SerialAlgorithm},
 * providing methods such as {@link #next()} and {@link #setAutoRunning}.</p>
 *
 * @see SerialAlgorithm
 */
public class SerialExecutor {
	private SerialAlgorithm<?> mAlgorithm;
	private ScheduledExecutorService mExecutor;
	private ScheduledFuture<?> mAutoHandle;
	private long mAutoStep;

	private BooleanProperty mAutoRun;

	private static final ThreadFactory sThreadFactory = r -> {
		final Thread t = new Thread(r);

		t.setDaemon(true);

		if (r instanceof AutoRunTask)
			t.setName("Auto run task");
		else if (r instanceof SerialAlgorithm)
			t.setName("Serial algorithm");

		return t;
	};

	/**
	 * @param algorithm {@code SerialAlgorithm} to be executed on a background
	 * thread.
	 * @param autoStep Delay amount in milliseconds to wait when running the
	 * algorithm automatically.
	 */
	public SerialExecutor (SerialAlgorithm<?> algorithm, long autoStep) {
		mAlgorithm = algorithm;
		mExecutor = new ScheduledThreadPoolExecutor(2, sThreadFactory);
		mAutoStep = autoStep;

		mAutoRun = new SimpleBooleanProperty(this, "autoRunning", false);

		mAlgorithm.stoppedProperty().addListener(
			(observable, wasStopped, stopped) -> {
				if (!wasStopped && stopped)
					mExecutor.shutdown();
			}
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

	/**
	 * <p>Invoked by a {@code SerialExecutor} user to progress by one single
	 * step the algorithm execution.</p>
	 * <p>This method produces no visible effects if the algorithm is no longer
	 * running.</p>
	 */
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

	/**
	 * @param autoRunning {@code true} if the executor should advance the
	 * {@link SerialAlgorithm} automatically by the given delay, {@code false
	 * } otherwise.
	 */
	public void setAutoRunning (boolean autoRunning) {
		if (autoRunning)
			runAuto();
		else
			pauseRunAuto();

		mAutoRun.set(autoRunning);
	}

	/**
	 * @return The value of the {@code autoRunning} property.
	 */
	public boolean isAutoRunning () {
		return mAutoRun.get();
	}

	public ReadOnlyBooleanProperty autoRunningProperty () {
		return mAutoRun;
	}

	/**
	 * <p></p>Utility method intended to submit small, <i>synchronous</i> tasks to
	 * the JavaFX UI thread.</p>
	 * <p>This might be chosen as an alternative to {@link Platform#runLater}
	 * when it is necessary to wait for a task to complete, differently to
	 * {@link Platform#runLater} which runs the task in parallel to the
	 * thread submitting it.
	 *
	 * @param action Code to invoke on the UI thread.
	 */
	public static void runAndWait (Runnable action) {
		final CountDownLatch doneLatch = new CountDownLatch(1);

		if (action != null) {
			if (Platform.isFxApplicationThread()) {
				action.run();
			} else {
				Platform.runLater(() -> {
					try {
						action.run();
					} finally {
						doneLatch.countDown();
					}
				});

				try {
					doneLatch.await();
				} catch (InterruptedException e) {
					// Ignore exception
				}
			}
		} else {
			throw new NullPointerException("action was null");
		}
	}

	private class AutoRunTask implements Runnable {
		@Override
		public void run() {
			next();
		}
	}
}
