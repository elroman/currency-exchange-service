package com.elroman.ces.controllers;

import com.elroman.ces.models.Currency;
import com.elroman.ces.models.Rate;
import com.elroman.ces.models.RateSource;
import com.elroman.ces.models.dao.RateDao;
import com.elroman.ces.service.RateService;
import com.elroman.ces.service.WorkerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RateControllerTest {
	private final String uahName = "UAH";
	private final String eurName = "EUR";
	private final Currency currUAH = new Currency(980, uahName, "Ukraine Hryvnia");
	private final Currency currEUR = new Currency(978, eurName, "Euro");
	private final BigDecimal rateValue = new BigDecimal("34.1");
	private final RateSource rs = new RateSource("finance.ua", "http://resources.finance.ua/ru/public/currency-cash.json", currUAH, true);
	private final Rate rateTest = new Rate(currUAH, currEUR, rateValue, rs);
	private RateController controller;

	@Mock
	private RateDao rateDao;

	@Mock
	private WorkerService workerService;

	@Mock
	private RateService rateService;

	@Before
	public void setUp() throws Exception {
		controller = new RateController(rateDao, workerService, rateService);
	}

	@Test
	public void testGetById() throws Exception {
		// Given
		String id = "test_rate_id";
		Rate rate = rateTest;
		when(rateDao.getById(id)).thenReturn(rate);

		// When
		String rateActual = controller.getById(id);

		// Then
		assertThat(rateActual).isEqualTo(rate.toJson());
	}

	@Test
	public void testGetLatestRate() throws Exception {
		// Given
		when(rateService.getLatestRateByCurrencyNames(eurName, uahName)).thenReturn(rateTest);

		// When
		String rateActual = controller.getLatestRate(eurName, uahName);

		// Then
		assertThat(rateActual).isEqualTo(rateTest.toJson());
	}

	@Test
	public void testGetLatestRateByDate() throws Exception {
		// Given
		Date dateTo = new Date();
		when(rateService.getLatestRateByDate(eurName, uahName, dateTo.toString())).thenReturn(rateTest);

		// When
		String rateActual = controller.getLatestRateByDate(eurName, uahName, dateTo.toString());

		// Then
		assertThat(rateActual).isEqualTo(rateTest.toJson());
	}

	@Test
	public void testGetLatestRateByTime() throws Exception {
		// Given
		Date dateTo = new Date();
		String timeTo = "15-32";
		when(rateService.getLatestRateByTime(eurName, uahName, dateTo.toString(), timeTo)).thenReturn(rateTest);

		// When
		String rateActual = controller.getLatestRateByTime(eurName, uahName, dateTo.toString(), timeTo);

		// Then
		assertThat(rateActual).isEqualTo(rateTest.toJson());
	}

	@Test
	public void testRefreshInfo() throws Exception {
		// Given
		doNothing().when(workerService).updateRatesFromActiveSources();

		// When
		String resultActual = controller.refreshInfo();

		// Then
		assertThat(resultActual).isEqualTo("Refresh rates info has done!");
	}

	@Test
	public void testDeleteRate() throws Exception {
		// Given
		String id = "test_rate_id";

		// When
		String resultActual = controller.delete(id);

		// Then
		assertThat(resultActual).isEqualTo("Rate was successfully deleted!");
	}
}
