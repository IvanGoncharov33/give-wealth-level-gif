package ru.goncharov.app.service.feignclient;

import ru.goncharov.app.dto.GiphyMetaDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "giphy-client", url = "${giphy.url}")
@RequestMapping("/v1/gifs")
public interface GiphyFeignClient {

    @GetMapping("/search?q=rich&api_key=${giphy.appId}&limit=1&offset={random}&lang=${giphy.lang}")
    GiphyMetaDataDto getRandomGifRich(@PathVariable("random") String random);

    @GetMapping("/search?q=broke&api_key=${giphy.appId}&limit=1&offset={random}&lang=${giphy.lang}")
    GiphyMetaDataDto getRandomGifBroke(@PathVariable("random") String random);
}
