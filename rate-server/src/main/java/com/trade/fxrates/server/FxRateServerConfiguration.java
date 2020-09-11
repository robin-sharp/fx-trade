package com.trade.fxrates.server;

import com.trade.fxrates.server.source.OpenFxRateSource;
import com.trade.fxrates.server.source.ScheduledFxRateProducer;
import com.trade.fxrates.server.source.ScheduledFxRateProducerRandomiser;
import com.trade.rates.FxRate;
import com.trade.rates.product.RateProduct;
import com.trade.rates.product.RateProductService;
import com.trade.rates.product.ScheduledRateProductProducer;
import com.trade.rates.server.websocket.WebSocketRatePublisher;
import com.trade.rates.util.QueueSubscriptionManager;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.temporal.ChronoUnit;

@Configuration
@EnableScheduling
public class FxRateServerConfiguration {

	@Autowired
	private BeanFactory beanFactory;

	@Bean
	public RateProductService getRateProductService() {
		return new RateProductService();
	}

	@Bean
	public OpenFxRateSource openFxRateSource() {
		return new OpenFxRateSource("rates/fxrates/usdrates.json");
	}

	@Bean("ScheduledOpenFxRateProducer")
	public ScheduledFxRateProducer scheduledOpenFxRateProducer(
			OpenFxRateSource openFxRateSource,
			QueueSubscriptionManager subscriptionManager) {
		ScheduledFxRateProducer fxRateProducer = new ScheduledFxRateProducerRandomiser(openFxRateSource);
		fxRateProducer.setBeanFactory(beanFactory);
		fxRateProducer.start(1, ChronoUnit.SECONDS);
		fxRateProducer.setOutputChannel(openFxRateSubscribableChannel());
		return fxRateProducer;
	}

	@Bean("OpenFxRateSubscribableChannel")
	public SubscribableChannel openFxRateSubscribableChannel() {
		return new DirectChannel();
	}

	@Bean("InternalScheduledFxRateProductProducer")
	public ScheduledRateProductProducer internalScheduledFxRateProductProducer() {
		RateProduct rateProduct = getRateProductService().getRateProduct("Internal");
		ScheduledRateProductProducer<FxRate> scheduledRateProductProducer = new ScheduledRateProductProducer<>(rateProduct);
		scheduledRateProductProducer.setBeanFactory(beanFactory);
		scheduledRateProductProducer.start(1, ChronoUnit.SECONDS);
		openFxRateSubscribableChannel().subscribe(scheduledRateProductProducer.directHandler());
		scheduledRateProductProducer.setOutputChannel(internalFxRateProductSubscribableChannel());
		return scheduledRateProductProducer;
	}

	@Bean("InternalFxRateProductSubscribableChannel")
	public SubscribableChannel internalFxRateProductSubscribableChannel() {
		return new DirectChannel();
	}

	@Bean("WebSocketRatePublisher")
	public WebSocketRatePublisher<FxRate> webSocketFxRatePublisher() {
		WebSocketRatePublisher<FxRate> webSocketRatePublisher = new WebSocketRatePublisher<>("/topic/rates/fxrates/");
		internalFxRateProductSubscribableChannel().subscribe(webSocketRatePublisher.directHandler());
		return webSocketRatePublisher;
	}
}