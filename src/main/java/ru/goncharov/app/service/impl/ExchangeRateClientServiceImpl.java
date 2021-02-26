package ru.goncharov.app.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.goncharov.app.dto.Wealth;
import ru.goncharov.app.service.ExchangeRateClientService;
import ru.goncharov.app.service.feignclient.ExchangeRateFeignClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A service for determining the welfare level by exchange rate of currency today and yesterday
 */
@AllArgsConstructor
@Service
public class ExchangeRateClientServiceImpl implements ExchangeRateClientService {

    private final ExchangeRateFeignClient exchangeRateFeignClient;

    /**
     * Get welfare level by exchange rate of currency today and yesterday
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return wealth level
     */
    @Override
    public Wealth getWealthDifferenceExchangeRate(String currencyCode) {
        final String currency = currencyCode.toUpperCase();
        if (getExchangeRateToday(currency) > getExchangeRateYesterday(currency)){
            return Wealth.RICH;
        }else return Wealth.BROKE;
    }

    /**
     * Get today's exchange rate of currency
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return today's exchange rate
     */
    protected double getExchangeRateToday(String currencyCode){
      return exchangeRateFeignClient.getExchangeRateToday(currencyCode).getRates().get(currencyCode);
    }

    /**
     * Get yesterday's exchange rate of currency
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return yesterday's exchange rate
     */
    protected double getExchangeRateYesterday(String currencyCode){
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        String yesterday = yesterdayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return exchangeRateFeignClient.getHistoricalExchangeRate(currencyCode, yesterday).getRates().get(currencyCode);
    }
}
