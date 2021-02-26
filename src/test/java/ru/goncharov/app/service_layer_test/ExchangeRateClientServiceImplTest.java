package ru.goncharov.app.service_layer_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.goncharov.app.dto.ExchangeRateDto;
import ru.goncharov.app.dto.Wealth;
import ru.goncharov.app.service.ExchangeRateClientService;
import ru.goncharov.app.service.feignclient.ExchangeRateFeignClient;
import ru.goncharov.app.service.impl.ExchangeRateClientServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateClientServiceImplTest {

    private ExchangeRateClientService sut;
    @Mock
    private ExchangeRateFeignClient exchangeRateFeignClient;
    private ExchangeRateDto exchangeRateDtoWithTodayExchangeRate;
    private ExchangeRateDto getExchangeRateDtoWithYesterdayExchangeRate;
    private final String currencyCode = "EUR";

    @BeforeEach
    void setUp() {
        Map<String, Double> mapWithTodayExchangeRate = new HashMap<>();
        Map<String, Double> mapWithYesterdayExchangeRate = new HashMap<>();
        sut = new ExchangeRateClientServiceImpl(exchangeRateFeignClient);
        final String baseCurrencyCode = "RUB";
        exchangeRateDtoWithTodayExchangeRate = new ExchangeRateDto(baseCurrencyCode, mapWithTodayExchangeRate);
        getExchangeRateDtoWithYesterdayExchangeRate = new ExchangeRateDto(baseCurrencyCode, mapWithYesterdayExchangeRate);
    }

    @Test
    void shouldGetRichWhenExchangeRateHigherToday() {
        exchangeRateDtoWithTodayExchangeRate.getRates().put(currencyCode, 10.0);
        getExchangeRateDtoWithYesterdayExchangeRate.getRates().put(currencyCode, 8.0);
        when(exchangeRateFeignClient.getExchangeRateToday(any(String.class)))
                .thenReturn(exchangeRateDtoWithTodayExchangeRate);
        when(exchangeRateFeignClient.getHistoricalExchangeRate(any(String.class), any(String.class)))
                .thenReturn(getExchangeRateDtoWithYesterdayExchangeRate);
        Assertions.assertEquals(Wealth.RICH, sut.getWealthDifferenceExchangeRate(currencyCode));
    }

    @Test
    void shouldGetBrokeWhenExchangeRateHigherYesterday() {
        exchangeRateDtoWithTodayExchangeRate.getRates().put(currencyCode, 8.0);
        getExchangeRateDtoWithYesterdayExchangeRate.getRates().put(currencyCode, 10.0);
        when(exchangeRateFeignClient.getExchangeRateToday(any(String.class)))
                .thenReturn(exchangeRateDtoWithTodayExchangeRate);
        when(exchangeRateFeignClient.getHistoricalExchangeRate(any(String.class), any(String.class)))
                .thenReturn(getExchangeRateDtoWithYesterdayExchangeRate);
        Assertions.assertEquals(Wealth.BROKE, sut.getWealthDifferenceExchangeRate(currencyCode));
    }
}