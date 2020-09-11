package com.trade.fxrates.server.source;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import static com.trade.EnvProfile.TEST_PROFILE;
import static com.trade.JUnitTag.UNIT_TEST;

@Tag(UNIT_TEST)
@Profile(TEST_PROFILE)
@ExtendWith(MockitoExtension.class)
class ScheduledFxRateSourceTest {
/*
	@InjectMocks
	private ScheduledFxRateSource scheduledFxRateSource;

	@Mock
	private FxRateSource fxRateSource;

	@Mock
	private ChannelEndPoint<FxRate> rateSink;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRatePump() throws InterruptedException {
		final String startRate = "1.5";
		final int pumps = 5;

		//when(fxRateSource.getBaseCurrency()).thenReturn("USD");
		when(fxRateSource.getQuote("GBP")).thenReturn(
				new FxRate("GBPUSD", FxDate.SPOT,0L,false,new BidOffer(new BigDecimal(startRate+"5"),new BigDecimal(startRate+"5"))));

		final CountDownLatch latch = new CountDownLatch(pumps);
		doAnswer((invocation) -> {
			Object[] args = invocation.getArguments();
			System.out.println("called with arguments: " + Arrays.toString(args));
			latch.countDown();
			return null;
		}).when(rateSink).send(any(FxRate.class));

		//scheduledFxRateSource.setSpotCurrencyPairs(Arrays.asList("GBP"));
		scheduledFxRateSource.start(10, 100, TimeUnit.MILLISECONDS);

		latch.await();

		scheduledFxRateSource.stop();

		final ArgumentCaptor<FxRate> fxRateCapture = ArgumentCaptor.forClass(FxRate.class);
		verify(rateSink, times(pumps)).send(fxRateCapture.capture());

		final FxRate fxRate = fxRateCapture.getAllValues().get(0);
		assertEquals("USDGBP", fxRate.getCurrencyPair());
		assertFalse(fxRate.isStale());

		final BidOffer bidOffer = fxRate.getRate(BigDecimal.ONE);
		assertTrue(bidOffer.getBid().toString().startsWith(startRate));
		assertTrue(bidOffer.getOffer().toString().startsWith(startRate));
	}
	*/
}