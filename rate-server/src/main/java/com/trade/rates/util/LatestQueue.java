package com.trade.rates.util;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Objects.requireNonNull;

/**
 * Simple Queue whereby if a value exists then it is removed and the newer version is added on the
 * end of the queue. For example this can be used to ensure only the latest rate is published.
 * <p>
 * This assumes the value has the unique key.
 */
public class LatestQueue<V> {

	private final Lock updateLock = new ReentrantLock();
	private LinkedHashMap<V, V> queue = new LinkedHashMap<>();

	public V add(V v) {
		requireNonNull(v, "value cannot be null");
		updateLock.lock();
		final V last = queue.remove(v);
		queue.put(v, v);
		updateLock.unlock();
		return last;
	}

	/**
	 * Remove the next element off the queue
	 */
	public V remove() {
		updateLock.lock();
		final Optional<V> v = queue.values().stream().findFirst();
		if (v.isPresent()) {
			queue.remove(v.get());
		}
		updateLock.unlock();
		return v.orElse(null);
	}

	public int size() {
		updateLock.lock();
		final int size = queue.size();
		updateLock.unlock();
		return size;
	}

	@Override
	public String toString() {
		updateLock.lock();
		final String string = queue.toString();
		updateLock.unlock();
		return string;
	}
}
