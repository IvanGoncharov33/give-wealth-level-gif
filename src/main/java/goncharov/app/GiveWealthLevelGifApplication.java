package goncharov;

import goncharov.config.IgnoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(excludeFilters =@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IgnoreConfig.class))
public class GiveWealthLevelGifApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiveWealthLevelGifApplication.class, args);
    }

}
