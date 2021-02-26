package ru.goncharov.app.controller;

import ru.goncharov.app.config.WrongCurrencyCodeException;
import ru.goncharov.app.service.GifService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for getting GIF
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class GifController {

   private final GifService gifService;

    /**
     * Get gif
     * @param currencyCode to find the exchange rate in relation to the base currency.
     * @return response entity with response body as byte array containing the GIF
     */
    @GetMapping(value = "/gif/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<byte[]> getGif(@PathVariable String currencyCode){

        if(!checkCurrencyCode(currencyCode)){
            throw new WrongCurrencyCodeException("Wrong currency code entered. Example: USD");
        }
        return new ResponseEntity<>(gifService.getGif(currencyCode), HttpStatus.OK);
    }

    private boolean checkCurrencyCode(String currencyCode){
        final String regex = "[a-zA-Z]{3}";
        final Pattern pattern = java.util.regex.Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(currencyCode);
        return matcher.matches();
    }
}
