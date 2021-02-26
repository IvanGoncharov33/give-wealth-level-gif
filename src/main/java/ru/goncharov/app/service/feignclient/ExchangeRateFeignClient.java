package ru.goncharov.app.service.feignclient;

import ru.goncharov.app.dto.ExchangeRateDto;
import ru.goncharov.app.config.ExchangeRateFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client to get the currency exchange rate
 */
@FeignClient(name = "exchange-rate", url = "${exchange.url}", configuration = ExchangeRateFeignClientConfig.class)
public interface ExchangeRateFeignClient {

    /**
     * Get today's currency exchange rate
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return dto with today's exchange rate
     */
    @GetMapping("/api/latest.json?base=${exchange.basedCurrency}&symbols={currencyCode}")
    ExchangeRateDto getExchangeRateToday(@PathVariable String currencyCode);

    /**
     * Get exchange rate on specified date
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @param date date on which exchange rate is being searched for in format(yyyy-MM-dd).
     * @return dto with exchange rate on specified date
     */
    @GetMapping("/api/historical/{date}.json?base=${exchange.basedCurrency}&symbols={currencyCode}")
    ExchangeRateDto getHistoricalExchangeRate(@PathVariable String currencyCode, @PathVariable String date);
}
