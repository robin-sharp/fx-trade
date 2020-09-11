package com.trade.rates.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static com.trade.JUnitTag.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag(UNIT_TEST)
class LatestQueueTest {

	@Test
	public void testEmptyQueue() {
		final LatestQueue<Integer> queue = new LatestQueue<>();
		assertNull(queue.remove());
		assertEquals(0,queue.size());
	}

	@Test
	public void testAddThenRemoveQueueItems() {
		final LatestQueue<Integer> queue = new LatestQueue<>();

		IntStream.rangeClosed(1, 10).forEach(i -> {
			assertNull(queue.add(i));
		});

		assertEquals(10, queue.size());

		IntStream.rangeClosed(1, 10).forEach(i -> {
			assertEquals(i, queue.remove());
		});

		assertEquals(0, queue.size());
	}

	@Test
	public void testAddThenReverseThenRemoveQueueItems() {
		final LatestQueue<Integer> queue = new LatestQueue<>();

		IntStream.rangeClosed(1, 10).forEach(i -> {
			assertNull(queue.add(i));
		});

		assertEquals(10, queue.size());

		IntStream.rangeClosed(-10, -1).map(i -> -i).forEach(i -> {
			assertEquals(i, queue.add(i));
		});

		assertEquals(10, queue.size());

		IntStream.rangeClosed(-10, -1).map(i -> -i).forEach(i -> {
			assertEquals(i, queue.remove());
		});

		assertEquals(0, queue.size());
	}

	@Test
	public void testQueueItems() throws InterruptedException {

		final int size = 10;
		final LatestQueue<Integer> queue = new LatestQueue<>();
		IntStream.rangeClosed(1, size).forEach(i -> {
			queue.add(i);
		});

		assertEquals(size, queue.size(), "" + queue);

		int count = 1000;
		CountDownLatch latch = new CountDownLatch(size*count);

		IntStream.rangeClosed(1, size).forEach(l -> {
			new Thread(() -> {
				//Apply 100n random ints between 1 and 10
				new Random().ints(count, 1, size).forEach(i -> {
					queue.add(i);
					latch.countDown();
				});
			}).start();
		});
		latch.await();

		assertEquals(size, queue.size(), "" + queue);

		IntStream.rangeClosed(1, size).forEach(i -> {
			queue.remove();
		});
		assertEquals(0, queue.size(), "" + queue);
	}
}