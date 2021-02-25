package goncharov.app.service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@FeignClient(name = "gif-download-client",url = "{basedUrl}")
public interface FeignClientGif {

   @GetMapping("{urn}")
    byte[] downloadGif(URI basedUrl, @PathVariable String urn);
}
