package com.trade.fxrates.server.source;

import com.trade.rates.FxRate;
import com.trade.rates.server.websocket.WebSocketSubscription;
import com.trade.rates.util.QueueSubscriptionManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.trade.fxrates.FxRateHeaders.FX_RATE_SOURCE;
import static java.lang.String.format;

/**
 * Class designed to read from an FxRateSource and pumping rates out to subscribers, based on which currency pairs
 * are currently subscribed to.
 */
@Slf4j
@NoArgsConstructor
public class ScheduledFxRateProducer extends MessageProducerSupport {

	private FxRateSource fxRateSource;

	@Autowired
	private QueueSubscriptionManager<WebSocketSubscription> subscriptionManager;

	public ScheduledFxRateProducer(FxRateSource fxRateSource) {
		this.fxRateSource = fxRateSource;
	}

	public void start(int period, TemporalUnit timeUnit) {
		final Duration duration = Duration.of(period, timeUnit);
		log.info("Schedule Process Source Rates source={}, period={} timeUnit={} duration={},",
				fxRateSource.getSourceName(), period, timeUnit, duration);

		getTaskScheduler().scheduleWithFixedDelay(() -> {
			processRates();
		}, duration);
	}

	void processRates() {
		final AtomicInteger count = new AtomicInteger();
		subscriptionManager.forEachSubscribedTopic(topic -> {
			try {
				String currencyPair = topic.substring(0, 6);
				FxRate fxRate = fxRateSource.getQuote(currencyPair);
				if (fxRate == null) {
					throw new IllegalStateException(format("FxRate %s not found currencyPair=", currencyPair));
				}

				final FxRate newFxRate = newFxRate(currencyPair, fxRate);
				send(newFxRate);
				count.incrementAndGet();
				log.debug("Source fxRate={}", fxRate);
			} catch (Throwable e) {
				log.error(format("Failed to handle rate topic=%s", topic), e);
			}
		});
		if (count.get() > 0) {
			log.info("Processed Rates source={} count={}", fxRateSource.getSourceName(), count);
		}
	}

	boolean send(FxRate fxRate) {
		try {
			final Message<FxRate> message = MessageBuilder.
					withPayload(fxRate).
					setHeader(FX_RATE_SOURCE, fxRateSource.getSourceName()).
					build();
			getOutputChannel().send(message);
			log.debug("Sent fxRate={} from={}", fxRate, getBeanName());
		} catch (Throwable t) {
			log.error(format("Failed to send fxRate=%s", fxRate), t);
		}
		return true;
	}

	protected FxRate newFxRate(String currencyPair, FxRate fxRate) {
		return fxRate;
	}
}