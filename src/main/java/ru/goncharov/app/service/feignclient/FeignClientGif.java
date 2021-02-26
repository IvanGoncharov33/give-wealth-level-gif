package ru.goncharov.app.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

/**
 * Client to receive GIF
 */
@FeignClient(name = "gif-download-client",url = "{basedUrl}")
public interface FeignClientGif {

    /**
     * Get GIF
     * @param basedUrl based URI source GIF
     * @param urn URN source GIF
     * @return GIF as byte array
     */
   @GetMapping("{urn}")
    byte[] downloadGif(URI basedUrl, @PathVariable String urn);
}
