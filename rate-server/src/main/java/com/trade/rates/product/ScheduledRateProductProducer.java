package com.trade.rates.product;


import com.trade.Rate;
import com.trade.rates.util.LatestQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.trade.fxrates.FxRateHeaders.FX_RATE_PRODUCT;
import static java.lang.String.format;

/**
 * Class used to schedule sending updates to rates for different products.
 * <p>
 * FxRate changes overwrite each other in the LatestQueue.
 *
 * @param <T>
 */
@Slf4j
public class ScheduledRateProductProducer<T extends Rate> extends MessageProducerSupport {

	private final LatestQueue<T> latestRates = new LatestQueue<>();
	private RateProduct rateProduct;

	public ScheduledRateProductProducer(RateProduct rateProduct) {
		this.rateProduct = rateProduct;
	}

	public void start(int period, TemporalUnit timeUnit) {
		final Duration duration = Duration.of(period, timeUnit);
		log.info("Schedule Source productName={}, period={} timeUnit={}  duration={}",
				rateProduct.getProductName(), period, timeUnit, duration);
		getTaskScheduler().scheduleWithFixedDelay(() -> {
			processLatestRates();
		}, duration);
	}

	void processLatestRates() {
		final AtomicInteger count = new AtomicInteger();
		try {
			T latestRate;
			while ((latestRate = latestRates.remove()) != null) {
				final T fxRate = (T) latestRate.spread(rateProduct.getSpreadStructure());
				send(fxRate);
				count.incrementAndGet();
			}
		} catch (Throwable e) {
			log.error("Failed to process fxRates", e);
		}
		if (count.get() > 0) {
			log.info("Processed Rates product={} count={}", rateProduct.getProductName(), count);
		}
	}

	boolean send(T rate) {
		try {
			final Message<T> message = MessageBuilder.
					withPayload(rate).
					setHeader(FX_RATE_PRODUCT, rateProduct.getProductName()).
					//setHeader(FX_RATE_SOURCE, fxRateSource.getSourceName()). --inbound message
							build();
			getOutputChannel().send(message);
			log.debug("Sent fxRate={} from={}", rate, getBeanName());
		} catch (Throwable t) {
			log.error(format("Failed to send rate=%s", rate), t);
		}
		return true;
	}

	void handle(T rate) {
		latestRates.add(rate);
	}

	public MessageHandler directHandler() {
		return message -> handle((T) message.getPayload());
	}
}
