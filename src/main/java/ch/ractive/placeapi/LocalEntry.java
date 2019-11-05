package ch.ractive.placeapi;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LocalEntry {
    @JsonProperty("displayed_where")
    private String displayedWhere;
    @JsonProperty("displayed_what")
    private String displayedWhat;

    @Data
    public static class OpeningHours {
        @Data @AllArgsConstructor
        public static class OpenRange {
            private LocalTime start;
            private LocalTime end;
            private String type;
        }

        @JsonProperty("days")
        private Map<String, List<OpenRange>> days;
    }

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
}
