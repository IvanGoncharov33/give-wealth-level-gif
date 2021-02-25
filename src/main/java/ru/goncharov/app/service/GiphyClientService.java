package ru.goncharov.app.service;

import ru.goncharov.app.dto.Wealth;

public interface GiphyClientService {
    String getUrlWithWealthDifferenceGif(Wealth wealth);
}
