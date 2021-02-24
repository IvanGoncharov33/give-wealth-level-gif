package goncharov.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
