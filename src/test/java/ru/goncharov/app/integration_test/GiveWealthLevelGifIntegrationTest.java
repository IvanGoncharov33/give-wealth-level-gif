package ru.goncharov.app.integration_test;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.goncharov.app.controller.GifController;
import ru.goncharov.app.wiremock.clientgif.GifClientMock;
import ru.goncharov.app.wiremock.exchange.ExchangeRateClientMock;
import ru.goncharov.app.wiremock.giphy.GiphyClientMock;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GiveWealthLevelGifIntegrationTest {

    private WireMockServer mockExchangeClient;
    private WireMockServer mockGiphyClient;
    private WireMockServer mockGifClient;
    @Autowired
    private GifController gifController;

    @Value("${exchange.currencyCode}")
    private String currencyCode;
    private byte[] richGif;
    private byte[] brokeGif;

    @BeforeEach
    void setUp() throws IOException {
        richGif = Objects.requireNonNull(
                GiveWealthLevelGifIntegrationTest.class
                        .getClassLoader()
                        .getResourceAsStream("richtest/RichResponse.gif")).readAllBytes();
        brokeGif = Objects.requireNonNull(
                GiveWealthLevelGifIntegrationTest.class
                        .getClassLoader()
                        .getResourceAsStream("broketest/BrokeResponse.gif")).readAllBytes();
        mockExchangeClient = new WireMockServer(9561);
        mockExchangeClient.start();
        mockGiphyClient = new WireMockServer(9562);
        mockGiphyClient.start();
        mockGifClient = new WireMockServer(9563);
        mockGifClient.start();
        GiphyClientMock.setUpGiphyClientResponse(mockGiphyClient);
        GifClientMock.setUpDownloadGif(mockGifClient, richGif, brokeGif);
    }

    @Test
    void shouldGetGifWealthLevelBroke() throws IOException {
        ExchangeRateClientMock.setUpExchangeRateWealthLevelBrokeResponse(mockExchangeClient);
        assertArrayEquals(brokeGif, gifController.getGif(currencyCode).getBody());
    }

    @Test
    void shouldGetGifWealthLevelRich() throws IOException {
        ExchangeRateClientMock.setUpExchangeRateWealthLevelRichResponse(mockExchangeClient);
        assertArrayEquals(richGif, gifController.getGif(currencyCode).getBody());
    }

    @AfterEach
    void close() {
        mockExchangeClient.stop();
        mockGiphyClient.stop();
        mockGifClient.stop();
    }
}