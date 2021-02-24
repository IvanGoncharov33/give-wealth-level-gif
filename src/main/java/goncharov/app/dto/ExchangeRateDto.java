package goncharov.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRateDto {
    private String base;

    @JsonProperty("rates")
    private Map<String, Double> rates;
}
