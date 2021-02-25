package ru.goncharov.app.service;

import ru.goncharov.app.dto.Wealth;

public interface ExchangeRateClientService {
    Wealth getWealthDifferenceExchangeRate(String currencyCode);
}
