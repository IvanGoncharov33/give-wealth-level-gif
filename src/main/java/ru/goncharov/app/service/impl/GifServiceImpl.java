package ru.goncharov.app.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.goncharov.app.service.ExchangeRateClientService;
import ru.goncharov.app.service.GifService;
import ru.goncharov.app.service.GiphyClientService;
import ru.goncharov.app.service.feignclient.FeignClientGif;

import java.net.URI;

/**
 * Service for getting GIF
 */
@AllArgsConstructor
@Service
public class GifServiceImpl implements GifService {

    private final ExchangeRateClientService exchangeRateClientService;
    private final GiphyClientService giphyClientService;
    private final FeignClientGif feignClientGif;

    /**
     * Get GIF
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return GIF as byte array
     */
    @Override
    public byte[] getGif(String currencyCode) {
        URI uri = getUriWithGif(currencyCode);
        URI basedUrl = getBasedUrl(uri);
        String urn = getUrn(uri);
        return feignClientGif.downloadGif(basedUrl, urn);
    }

    /**
     * Get URI for getting source GIF
     * @param currencyCode to find the exchange rate in relation to the base currency (ISO 4217:2008).
     * @return source GIF URI
     */
    protected URI getUriWithGif(String currencyCode){
       return URI.create(
               giphyClientService.getUrlWithWealthDifferenceGif(exchangeRateClientService.getWealthDifferenceExchangeRate(currencyCode)
               ));
    }

    private URI getBasedUrl(URI uri) {
        return URI.create(uri.getScheme() + "://" + uri.getAuthority());
    }

    private String getUrn(URI uri) {
        return uri.getPath();
    }
}
