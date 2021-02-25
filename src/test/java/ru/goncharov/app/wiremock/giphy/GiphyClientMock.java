package ru.goncharov.app.wiremock.giphy;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.util.StreamUtils.copyToString;

public class GiphyClientMock {

    public static void setUpGiphyClientResponse(WireMockServer mockService) throws IOException {

        mockService.stubFor(get(urlMatching("/v1/gifs/search\\?q=rich&.+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(GiphyClientMock.class
                                .getClassLoader().getResourceAsStream(
                                        "richtest/get-random-url-with-gif-rich.json"), Charset.defaultCharset()))));

        mockService.stubFor(get(urlMatching("/v1/gifs/search\\?q=broke&.+"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(GiphyClientMock.class
                                .getClassLoader().getResourceAsStream(
                                        "broketest/get-random-url-with-gif-broke.json"), Charset.defaultCharset()))));

    }
}
