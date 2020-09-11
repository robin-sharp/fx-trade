package com.trade.fxrates.server.source;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.trade.JUnitTag.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(UNIT_TEST)
public class OpenFxRateSourceTest {

	@Test
	public void testLoadUSDFxRates() {
		OpenFxRateSource.OpenFxRates fxRates = OpenFxRateSource.loadRates("rates/fxrates/usdrates.json");
		assertEquals(fxRates.disclaimer, "https://openexchangerates.org/terms/");
		assertEquals(fxRates.license, "https://openexchangerates.org/license/");
		assertEquals("USD", fxRates.base);
		assertEquals(1553248802, fxRates.timestamp);
		assertEquals(154, fxRates.rates.size());
		assertEquals("1", fxRates.rates.get("USD"));
		assertEquals("0.763", fxRates.rates.get("GBP"));
	}

	@Test
	public void testUSDQuote() {
		OpenFxRateSource openFxRateSource = new OpenFxRateSource("rates/fxrates/usdrates.json");
		assertEquals("USD", openFxRateSource.getBaseCurrency());
		assertEquals(1553248802, openFxRateSource.getTimestamp());
		assertEquals(154, openFxRateSource.getQuotedCurrencies().size());
		assertEquals(new BigDecimal("1.000000"), getBid(openFxRateSource, "USDUSD"));
		assertEquals(new BigDecimal("1.000000"), getOffer(openFxRateSource, "USDUSD"));

		//Parity Rates
		assertEquals(new BigDecimal("1.000000"), getBid(openFxRateSource, "GBPGBP"));
		assertEquals(new BigDecimal("1.000000"), getOffer(openFxRateSource, "GBPGBP"));

		//Reverse Rates
		assertEquals(new BigDecimal("0.763000"), getBid(openFxRateSource, "USDGBP"));
		assertEquals(new BigDecimal("0.763000"), getOffer(openFxRateSource, "USDGBP"));
		assertEquals(new BigDecimal("1.310616"), getBid(openFxRateSource, "GBPUSD"));
		assertEquals(new BigDecimal("1.310616"), getOffer(openFxRateSource, "GBPUSD"));

		//Cross Currency
		assertEquals(new BigDecimal("0.862634"), getBid(openFxRateSource, "EURGBP"));
		assertEquals(new BigDecimal("0.862634"), getOffer(openFxRateSource, "EURGBP"));
		assertEquals(new BigDecimal("1.159240"), getBid(openFxRateSource, "GBPEUR"));
		assertEquals(new BigDecimal("1.159240"), getOffer(openFxRateSource, "GBPEUR"));
	}

	BigDecimal getBid(OpenFxRateSource openFxRateSource, String currencyPair) {
		return openFxRateSource.getQuote(currencyPair).getRate(BigDecimal.ONE).getBid();
	}

	BigDecimal getOffer(OpenFxRateSource openFxRateSource, String currencyPair) {
		return openFxRateSource.getQuote(currencyPair).getRate(BigDecimal.ONE).getBid();
	}
}