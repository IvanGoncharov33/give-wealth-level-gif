package goncharov.app.controller;

import goncharov.app.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class GifController {

   private final GifService gifService;

    @GetMapping(value = "/gif/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<byte[]> getGif(@PathVariable String currencyCode){
        return new ResponseEntity<>(gifService.getGif(currencyCode), HttpStatus.OK);
    }
}
