package goncharov.app.service;

import goncharov.app.dto.Wealth;

public interface GiphyClientService {

    String getUrlWithWealthDifferenceGif(Wealth wealth);
}
