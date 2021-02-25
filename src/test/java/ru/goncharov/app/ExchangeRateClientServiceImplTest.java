package goncharov.app;

import goncharov.app.dto.ExchangeRateDto;
import goncharov.app.dto.Wealth;
import goncharov.app.service.ExchangeRateClientService;
import goncharov.app.service.feignclient.ExchangeRateFeignClient;
import goncharov.app.service.impl.ExchangeRateClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private String currencyCode;

    @BeforeEach
    void setUp() {
        Map<String, Double> mapWithTodayExchangeRate = new HashMap<>();
        Map<String, Double> mapWithYesterdayExchangeRate = new HashMap<>();
        currencyCode = "USD";
        sut = new ExchangeRateClientServiceImpl(exchangeRateFeignClient);
        exchangeRateDtoWithTodayExchangeRate = new ExchangeRateDto("RUB", mapWithTodayExchangeRate);
        getExchangeRateDtoWithYesterdayExchangeRate = new ExchangeRateDto("RUB", mapWithYesterdayExchangeRate);

    }

    @Test
    void shouldGetRichWhenExchangeRateHigherToday() {
        exchangeRateDtoWithTodayExchangeRate.getRates().put(currencyCode, 10.0);
        getExchangeRateDtoWithYesterdayExchangeRate.getRates().put(currencyCode, 8.0);
        when(exchangeRateFeignClient.getExchangeRateCurrent(any(String.class)))
                .thenReturn(exchangeRateDtoWithTodayExchangeRate);
        when(exchangeRateFeignClient.getYesterdayExchangeRate(any(String.class), any(String.class)))
                .thenReturn(getExchangeRateDtoWithYesterdayExchangeRate);
        Assertions.assertEquals(Wealth.RICH, sut.getWealthDifferenceExchangeRate(currencyCode));
    }

    @Test
    void shouldGetBrokeWhenExchangeRateHigherYesterday() {
        exchangeRateDtoWithTodayExchangeRate.getRates().put(currencyCode, 8.0);
        getExchangeRateDtoWithYesterdayExchangeRate.getRates().put(currencyCode, 10.0);
        when(exchangeRateFeignClient.getExchangeRateCurrent(any(String.class)))
                .thenReturn(exchangeRateDtoWithTodayExchangeRate);
        when(exchangeRateFeignClient.getYesterdayExchangeRate(any(String.class), any(String.class)))
                .thenReturn(getExchangeRateDtoWithYesterdayExchangeRate);
        Assertions.assertEquals(Wealth.BROKE, sut.getWealthDifferenceExchangeRate(currencyCode));
    }

}