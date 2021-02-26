package ru.goncharov.app.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class overrides logger.level in default config for feign client.
 */
@Configuration
public class DefaultFeignClientConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.BASIC;
    }
}
