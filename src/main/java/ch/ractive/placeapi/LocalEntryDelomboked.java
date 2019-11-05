package ch.ractive.placeapi;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalEntryDelomboked {
    @JsonProperty("displayed_where")
    private String displayedWhere;
    @JsonProperty("displayed_what")
    private String displayedWhat;

    public String getDisplayedWhere() {
        return this.displayedWhere;
    }

    public String getDisplayedWhat() {
        return this.displayedWhat;
    }

    public OpeningHours getOpeningHours() {
        return this.openingHours;
    }

    public void setDisplayedWhere(String displayedWhere) {
        this.displayedWhere = displayedWhere;
    }

    public void setDisplayedWhat(String displayedWhat) {
        this.displayedWhat = displayedWhat;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public static class OpeningHours {
        public Map<String, List<OpenRange>> getDays() {
            return this.days;
        }

        public void setDays(Map<String, List<OpenRange>> days) {
            this.days = days;
        }

        public static class OpenRange {
            private LocalTime start;
            private LocalTime end;
            private String type;

            public OpenRange(LocalTime start, LocalTime end, String type) {
                this.start = start;
                this.end = end;
                this.type = type;
            }

            public LocalTime getStart() {
                return this.start;
            }

            public LocalTime getEnd() {
                return this.end;
            }

            public String getType() {
                return this.type;
            }

            public void setStart(LocalTime start) {
                this.start = start;
            }

            public void setEnd(LocalTime end) {
                this.end = end;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        @JsonProperty("days")
        private Map<String, List<OpenRange>> days;
    }

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
}
