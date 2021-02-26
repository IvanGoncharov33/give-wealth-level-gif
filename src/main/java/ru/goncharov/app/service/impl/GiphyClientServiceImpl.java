package ru.goncharov.app.service.impl;

import ru.goncharov.app.service.GiphyClientService;
import ru.goncharov.app.service.feignclient.GiphyFeignClient;
import ru.goncharov.app.dto.Wealth;
import ru.goncharov.app.dto.GiphyMetaDataDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Service for getting source GIF
 */
@AllArgsConstructor
@Service
public class GiphyClientServiceImpl implements GiphyClientService {

    private final GiphyFeignClient giphyFeignClient;

    /**
     * Get source GIF depending on wealth level
     * @param wealth wealth level
     * @return source GIF URL in string format
     */
    @Override
    public String getUrlWithWealthDifferenceGif(Wealth wealth){
        String urlGif;
       if (wealth == Wealth.RICH) {
           urlGif = getUrl(giphyFeignClient.getRandomGifRich(getRandomNumber()));
        } else {
           urlGif = getUrl(giphyFeignClient.getRandomGifBroke(getRandomNumber()));
        }
        return urlGif;
    }

    private String getUrl(GiphyMetaDataDto giphyMetaDataDto){
        return Arrays.stream(giphyMetaDataDto.getData()).
                map(element->element.getImages().getOriginal().getUrl()).collect(Collectors.joining());
    }

    private String getRandomNumber(){
      return String.valueOf((int)(Math.random() * 1000));
    }
}
