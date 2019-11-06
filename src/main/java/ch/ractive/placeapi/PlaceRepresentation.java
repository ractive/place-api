package ch.ractive.placeapi;

import java.time.LocalTime;
import java.util.List;

import lombok.Value;

@Value
public class PlaceRepresentation {
    private String name;
    private String address;

	/**
	 * Allows presenting opening hours in a form, where weekdays with the same
	 * opening hours are "compacted", e.g. like:
	 *
	 *
	 * <table>
	 *     <tr><td>Montag</td><td>geschlossen</td></tr>
	 *     <tr><td>Dienstag - Freitag</td><td>11:30 - 15:00<br/>18:30 - 00:00</td></tr>
	 *     <tr><td>Samstag</td><td>18:00 - 00:00</td></tr>
	 *     <tr><td>Sonntag</td><td>11:30 - 15:00</td></tr>
	 * </table>
	 */
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
