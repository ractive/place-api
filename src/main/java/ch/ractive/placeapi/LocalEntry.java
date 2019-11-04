package ch.ractive.placeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class LocalEntry {
    @JsonProperty("displayed_where")
    private String displayedWhere;
    @JsonProperty("displayed_what")
    private String displayedWhat;

    @Data
    public static class Assets {
        @Data
        public static class OpeningHours {
            @Data
            public static class OpeningHoursDefinition {
                @Data
                public static class Days {
                    @Data
                    public static class OpenRange {
                        private LocalTime start;
                        private LocalTime end;
                        private String type;
                    }

                    @JsonProperty("monday")
                    private List<OpenRange> monday;
                    @JsonProperty("tuesday")
                    private List<OpenRange> tuesday;
                    @JsonProperty("wednesday")
                    private List<OpenRange> wednesday;
                    @JsonProperty("thursday")
                    private List<OpenRange> thursday;
                    @JsonProperty("friday")
                    private List<OpenRange> friday;
                    @JsonProperty("saturday")
                    private List<OpenRange> saturday;
                    @JsonProperty("sunday")
                    private List<OpenRange> sunday;
                }

                @JsonProperty("days")
                private Days days;
            }

            @JsonProperty("opening_hours")
            private OpeningHoursDefinition openingHours;
        }

        @JsonProperty("opening_hours")
        private OpeningHours openingHours;
    }

    @JsonProperty("assets")
    private Assets assets;
}
