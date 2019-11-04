package ch.ractive.placeapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalTime;
import java.util.List;

@Value
public class PlaceRepresentation {
    private String name;
    private String address;

    @Value
    public static class OpeningHoursRange {
        private String weekdayStart;
        private String weekdayEnd;

        @Value
        public static class LocalTimeRange {
            private LocalTime start;
            private LocalTime end;
        }
        private List<LocalTimeRange> localTimeRanges;
    }
    private List<OpeningHoursRange> openingHoursRanges;
}
