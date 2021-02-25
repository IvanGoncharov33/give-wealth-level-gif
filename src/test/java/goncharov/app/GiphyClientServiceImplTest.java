package goncharov.app;

import goncharov.app.dto.GiphyMetaDataDto;
import goncharov.app.dto.Wealth;
import goncharov.app.service.feignclient.GiphyFeignClient;
import goncharov.app.service.impl.GiphyClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiphyClientServiceImplTest {

    private GiphyClientServiceImpl sut;
    private final String expectedUrlRich = "www.testrich.com";
    private final String expectedUrlBroke = "www.testbroke.com";
    private GiphyMetaDataDto dtoDataRich;
    private GiphyMetaDataDto dtoDataBroke;
    @Mock
    private GiphyFeignClient giphyFeignClient;

    @BeforeEach
    void setUp() {
        sut = new GiphyClientServiceImpl(giphyFeignClient);
        GiphyMetaDataDto.Data[] dataArrayRich = {
                new GiphyMetaDataDto.Data(new GiphyMetaDataDto.Data.Images (new GiphyMetaDataDto.Data.Images.Original(expectedUrlRich)))
        };
        dtoDataRich = new GiphyMetaDataDto(dataArrayRich);
        GiphyMetaDataDto.Data[] dataArrayBroke = {
                new GiphyMetaDataDto.Data(new GiphyMetaDataDto.Data.Images(new GiphyMetaDataDto.Data.Images.Original(expectedUrlBroke)))
        };
        dtoDataBroke = new GiphyMetaDataDto(dataArrayBroke);
    }

    @Test
    void shouldGetUrlRich() {
        when(giphyFeignClient.getRandomGifRich(any(String.class))).thenReturn(dtoDataRich);
        String actualUrl = sut.getUrlWithWealthDifferenceGif(Wealth.RICH);
        Assertions.assertEquals(expectedUrlRich, actualUrl);
    }

    @Test
    void shouldGetUrlBroke() {
        when(giphyFeignClient.getRandomGifBroke(any(String.class))).thenReturn(dtoDataBroke);
        String actualUrl = sut.getUrlWithWealthDifferenceGif(Wealth.BROKE);
        Assertions.assertEquals(expectedUrlBroke, actualUrl);
    }

}