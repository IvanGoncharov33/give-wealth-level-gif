package goncharov.app.service.feignclient;

import goncharov.app.dto.ExchangeRateDto;
import goncharov.app.config.ExchangeRateFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-rate", url = "${exchange.url}", configuration = ExchangeRateFeignClientConfig.class)
public interface ExchangeRateFeignClient {

    @GetMapping("/api/latest.json?base=${exchange.basedCurrency}&symbols={currencyCode}")
    ExchangeRateDto getExchangeRateCurrent(@PathVariable String currencyCode);

    @GetMapping("/api/historical/{date}.json?base=${exchange.basedCurrency}&symbols={currencyCode}")
    ExchangeRateDto getYesterdayExchangeRate(@PathVariable String currencyCode,@PathVariable String date);

    @GetMapping("api/currencies.json")
    ExchangeRateDto getCurrencyCodes();
}
