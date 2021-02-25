package ru.goncharov.app.wiremock.clientgif;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GifClientMock {

    public static void setUpDownloadGif(WireMockServer mockService, byte[] richExpectedBody, byte[] brokeExpectedBody){

        mockService.stubFor(get(urlEqualTo("/gif/rich/rid%3Dgiphy.gif"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.IMAGE_GIF_VALUE)
                        .withBody(richExpectedBody)));

        mockService.stubFor(get(urlEqualTo("/gif/broke/rid%3Dgiphy.gif"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.IMAGE_GIF_VALUE)
                        .withBody(brokeExpectedBody)));
    }
}
