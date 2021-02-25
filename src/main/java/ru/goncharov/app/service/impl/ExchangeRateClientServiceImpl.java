package goncharov.app.service.impl;

import goncharov.app.service.ExchangeRateClientService;
import goncharov.app.dto.Wealth;
import goncharov.app.service.feignclient.ExchangeRateFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class ExchangeRateClientServiceImpl implements ExchangeRateClientService {

    private final ExchangeRateFeignClient exchangeRateFeignClient;

    @Override
    public Wealth getWealthDifferenceExchangeRate(String currencyCode) {
        final String currency = currencyCode.toUpperCase();
        if (getExchangeRateCurrent(currency) > getExchangeRateYesterday(currency)){
            return Wealth.RICH;
        }else return Wealth.BROKE;
    }
    
    protected double getExchangeRateCurrent(String currencyCode){
      return exchangeRateFeignClient.getExchangeRateCurrent(currencyCode).getRates().get(currencyCode);
    }

    protected double getExchangeRateYesterday(String currencyCode){
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        String yesterday = yesterdayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return exchangeRateFeignClient.getYesterdayExchangeRate(currencyCode, yesterday).getRates().get(currencyCode);
    }
}
