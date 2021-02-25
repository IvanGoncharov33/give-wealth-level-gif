package goncharov.app.service;

import goncharov.app.dto.Wealth;

public interface ExchangeRateClientService {

    Wealth getWealthDifferenceExchangeRate(String currencyCode);
}
