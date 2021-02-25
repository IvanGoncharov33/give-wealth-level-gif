package goncharov.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyMetaDataDto {
    private Data[] data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private Images images;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Images {
            private Original original;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Original {
                private String url;
            }
        }
    }
}
