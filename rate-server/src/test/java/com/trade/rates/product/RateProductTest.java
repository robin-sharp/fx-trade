package com.trade.rates.product;

import com.trade.Spread;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.rates.RatesTestData.*;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@Tag(UNIT_TEST)
class RateProductTest {

	@Test
	public void testConstructor() {
		RateProduct rateProduct = INTERNAL_PRODUCT;
		assertEquals(INTERNAL_PRODUCT_NAME, rateProduct.getProductName());
		assertEquals(2, rateProduct.getThrottleDuration());
		assertEquals(SECONDS, rateProduct.getThrottleTimeUnit());
		assertEquals(3, rateProduct.getSpreadStructure().size());

		checkSpread(rateProduct, ONE_MILLION, ZERO_SIX_FIVE, ZERO_SIX_FIVE);
		checkSpread(rateProduct, TWO_MILLION, ZERO_SIX_TWO, ZERO_SIX_TWO);
		checkSpread(rateProduct, THREE_MILLION, ZERO_SIX_ONE, ZERO_SIX_ONE);
	}

	void checkSpread(RateProduct rateProduct, BigDecimal amount, BigDecimal bid, BigDecimal offer) {
		Map<BigDecimal, Spread> spreads = rateProduct.getSpreadStructure();
		Spread spread = spreads.get(amount);
		assertNotNull(spread);
		assertEquals(bid, spread.getBidSpread());
		assertEquals(offer, spread.getOfferSpread());
	}

	@Test
	public void testEquals() {
		assertEquals(INTERNAL_PRODUCT, INTERNAL_PRODUCT);
		assertNotEquals(INTERNAL_PRODUCT, INTERBANK_PRODUCT);
		assertEquals(635054844, INTERNAL_PRODUCT.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("RateProduct{productName=Internal, throttleDuration=2, throttleTimeUnit=Seconds, spreadStructure={1000000=Spread{bidSpread=0.000005, offerSpread=0.000005, spreadType=0}, 2000000=Spread{bidSpread=0.000002, offerSpread=0.000002, spreadType=0}, 3000000=Spread{bidSpread=0.000001, offerSpread=0.000001, spreadType=0}}}", INTERNAL_PRODUCT.toString());
	}
}