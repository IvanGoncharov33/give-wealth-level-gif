package ru.goncharov.app.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.goncharov.app.service.ExchangeRateClientService;
import ru.goncharov.app.service.GifService;
import ru.goncharov.app.service.GiphyClientService;
import ru.goncharov.app.service.feignclient.FeignClientGif;

import java.net.URI;

@AllArgsConstructor
@Service
public class GifServiceImpl implements GifService {

    private final ExchangeRateClientService exchangeRateClientService;
    private final GiphyClientService giphyClientService;
    private final FeignClientGif feignClientGif;

    @Override
    public byte[] getGif(String currencyCode) {
        URI uri = getUriWithGif(currencyCode);
        URI basedUrl = getBasedUrl(uri);
        String urn = getUrn(uri);
        return feignClientGif.downloadGif(basedUrl, urn);
    }

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
