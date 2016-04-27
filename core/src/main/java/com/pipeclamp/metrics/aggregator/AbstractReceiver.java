package com.pipeclamp.metrics.aggregator;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.pipeclamp.api.Receiver;
/**
 * Counts items received and optionally invokes a callback on a specified schedule.
 *
 * @author bremed200
 *
 * @param <D>
 */
public abstract class AbstractReceiver<D extends Object> implements Receiver<D>{

	private int received;
//	private long lastReceived = -1;
	private ScheduledFuture<?> actionHandle;
	private ScheduledExecutorService scheduler;

	protected AbstractReceiver() {
		super();
	}

	@Override
	public int accept(D record) {
//		lastReceived = System.currentTimeMillis();
		return ++received;
	}

	/**
	 * @param periodSeconds
	 * @param delaySeconds
	 * @param action
	 */
	public void schedule(int periodSeconds, int delaySeconds, Runnable action) {

		cancelScheduler();

		scheduler = Executors.newScheduledThreadPool(9);
		actionHandle = scheduler.scheduleAtFixedRate(action, delaySeconds, periodSeconds, SECONDS);

//		final Runnable killer = new Runnable() {
//		       public void run() { actionHandle.cancel(true); }
//			};

//		scheduler.schedule(killer, 60 * 60, SECONDS);
	}

	public void cancelScheduler() {

		if (scheduler == null || actionHandle == null) return;

		actionHandle.cancel(true);

		scheduler = null;
		actionHandle = null;
	}

	@Override
	public int count() { return received; }

	@Override
	public void clear() { received = 0; }
}