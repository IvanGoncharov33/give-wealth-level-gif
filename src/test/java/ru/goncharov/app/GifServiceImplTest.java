package ru.goncharov.app;

import ru.goncharov.app.service.ExchangeRateClientService;
import ru.goncharov.app.service.GifService;
import ru.goncharov.app.service.GiphyClientService;
import ru.goncharov.app.service.feignclient.FeignClientGif;
import ru.goncharov.app.service.impl.GifServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GifServiceImplTest {

    private GifService sut;
    @Mock
    private ExchangeRateClientService exchangeRateClientService;
    @Mock
    private  GiphyClientService giphyClientService;
    @Mock
    private FeignClientGif feignClientGif;
    private byte[] expectedByte;
    private URI uri;

    @BeforeEach
    void setUp() {
        expectedByte = new byte[] {1,2,3,4,5,6,7,8,9,10};
        uri = URI.create("http://test.com/ZGH8VtTZMmnwzsYYMf/ytrfg.gif&cid=36894a51qfcm3jkowylqzecz87ytrfg.gif");
        sut = new GifServiceImpl(exchangeRateClientService,giphyClientService,feignClientGif);
    }

    @Test
    void shouldGetBytesExpected() {
        when(giphyClientService.getUrlWithWealthDifferenceGif(
                exchangeRateClientService.getWealthDifferenceExchangeRate(any(String.class))))
                .thenReturn(uri.toString());
        when(feignClientGif.downloadGif(any(URI.class), any(String.class))).thenReturn(expectedByte);
      byte[] actualResult = sut.getGif(any(String.class));
        assertEquals(expectedByte, actualResult);
    }
}