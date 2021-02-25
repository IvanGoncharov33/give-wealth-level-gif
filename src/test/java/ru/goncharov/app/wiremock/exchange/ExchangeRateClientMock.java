package ru.goncharov.app.wiremock.exchange;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.util.StreamUtils.copyToString;

public class ExchangeRateClientMock {

    public static void setUpExchangeRateWealthLevelRichResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(get(urlPathMatching("/api/latest.json"))
                .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ExchangeRateClientMock.class.getClassLoader()
                                        .getResourceAsStream(
                                                "richtest/get-today-exchange-rate-higher-response.json"), Charset.defaultCharset())
                        )));

        mockService.stubFor(get(urlPathMatching("/api/historical/\\d{4}-\\d{2}-\\d{2}.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ExchangeRateClientMock.class.getClassLoader()
                                                .getResourceAsStream(
                                                        "richtest/get-yesterday-exchange-rate-lower-response.json"),
                                Charset.defaultCharset())
                        )));
    }

    public static void setUpExchangeRateWealthLevelBrokeResponse(WireMockServer mockService) throws IOException {

        mockService.stubFor(get(urlPathMatching("/api/latest.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ExchangeRateClientMock.class.getClassLoader()
                                        .getResourceAsStream(
                                                "broketest/get-today-exchange-rate-lower-response.json"), Charset.defaultCharset())
                        )));

        mockService.stubFor(get(urlPathMatching("/api/historical/\\d{4}-\\d{2}-\\d{2}.json"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ExchangeRateClientMock.class.getClassLoader()
                                                .getResourceAsStream(
                                                        "broketest/get-yesterday-exchange-rate-higher-response.json"),
                                        Charset.defaultCharset())
                        )));
    }
}
