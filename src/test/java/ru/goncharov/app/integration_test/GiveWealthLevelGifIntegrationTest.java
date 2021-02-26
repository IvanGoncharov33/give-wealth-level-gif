package ru.goncharov.app.integration_test;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.goncharov.app.controller.GifController;
import ru.goncharov.app.wiremock.clientgif.GifClientMock;
import ru.goncharov.app.wiremock.exchange.ExchangeRateClientMock;
import ru.goncharov.app.wiremock.giphy.GiphyClientMock;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GiveWealthLevelGifIntegrationTest {

    private WireMockServer mockExchangeClient;
    private WireMockServer mockGiphyClient;
    private WireMockServer mockGifClient;
    @Autowired
    private GifController gifController;
    @Autowired
    private MockMvc mockMvc;

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
    void shouldGetGifWealthLevelBroke() throws Exception {
        ExchangeRateClientMock.setUpExchangeRateWealthLevelBrokeResponse(mockExchangeClient);
        assertArrayEquals(brokeGif, gifController.getGif(currencyCode).getBody());
    }

    @Test
    void shouldGetGifWealthLevelRich() throws Exception {
        ExchangeRateClientMock.setUpExchangeRateWealthLevelRichResponse(mockExchangeClient);
        assertArrayEquals(richGif, gifController.getGif(currencyCode).getBody());
    }

    @Test
    void gifControllerTest() throws Exception {
        ExchangeRateClientMock.setUpExchangeRateWealthLevelRichResponse(mockExchangeClient);
        mockMvc.perform(get("/api/gif/{currencyCode}",currencyCode)).andExpect(status().isOk());
    }

    @AfterEach
    void close() {
        mockExchangeClient.stop();
        mockGiphyClient.stop();
        mockGifClient.stop();
    }
}