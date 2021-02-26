package ru.goncharov.app.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Class overrides request interceptor for ExchangeRateFeignClient.
 */
@Configuration
@IgnoreConfig
public class ExchangeRateFeignClientConfig {

    @Value("${exchange.appId}")
    private String appId;

    @Bean
    public RequestInterceptor requestInterceptorExchangeRate(){
        return template -> template.header(HttpHeaders.AUTHORIZATION, String.format("Token %s", appId));
    }
}
