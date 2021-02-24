package goncharov.app.service.impl;

import goncharov.app.service.GiphyClientService;
import goncharov.app.service.feignclient.GiphyFeignClient;
import goncharov.app.dto.Wealth;
import goncharov.app.dto.GiphyMetaDataDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GiphyClientServiceImpl implements GiphyClientService {

    private final GiphyFeignClient giphyFeignClient;

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
