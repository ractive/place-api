package ch.ractive.placeapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ch.ractive.placeapi.LocalEntry.OpeningHours.OpenRange;
import ch.ractive.placeapi.PlaceRepresentation.OpeningHoursRange;
import ch.ractive.placeapi.PlaceRepresentation.OpeningHoursRange.LocalTimeRange;

class PlaceResourceTest {

    @Test
    void convert() {
        // given
        LocalEntry localEntry = new LocalEntry();

        LocalEntry.OpeningHours openingHours = new LocalEntry.OpeningHours();
        Map<String, List<OpenRange>> weekdays = new LinkedHashMap<>();
        weekdays.put(
                "tuesday", List.of(
                        new OpenRange(LocalTime.of(11, 30), LocalTime.of(15, 0), "OPEN"),
                        new OpenRange(LocalTime.of(18, 30), LocalTime.of(0, 0), "OPEN")
                ));
        weekdays.put(
                "wednesday", List.of(
                        new OpenRange(LocalTime.of(11, 30), LocalTime.of(15, 0), "OPEN"),
                        new OpenRange(LocalTime.of(18, 30), LocalTime.of(0, 0), "OPEN")
                ));
        weekdays.put(
                "thursday", List.of(
                        new OpenRange(LocalTime.of(11, 30), LocalTime.of(15, 0), "OPEN"),
                        new OpenRange(LocalTime.of(18, 30), LocalTime.of(0, 0), "OPEN")
                ));
        weekdays.put(
                "friday", List.of(
                        new OpenRange(LocalTime.of(11, 30), LocalTime.of(15, 0), "OPEN"),
                        new OpenRange(LocalTime.of(18, 30), LocalTime.of(0, 0), "OPEN")
                ));
        weekdays.put(
                "saturday", List.of(
                        new OpenRange(LocalTime.of(18, 0), LocalTime.of(0, 0), "OPEN")
                ));
        weekdays.put(
                "sunday", List.of(
                        new OpenRange(LocalTime.of(11, 30), LocalTime.of(15, 0), "OPEN")
                ));

        openingHours.setDays(weekdays);

        localEntry.setOpeningHours(openingHours);

        // when
        PlaceRepresentation placeRepresentation = PlaceResource.convert(localEntry);

        // then
        assertThat(placeRepresentation.getOpeningHoursRanges()).containsExactly(
                new OpeningHoursRange("tuesday", "friday", List.of(
                        new LocalTimeRange(LocalTime.of(11, 30), LocalTime.of(15, 0)),
                        new LocalTimeRange(LocalTime.of(18, 30), LocalTime.of(0, 0))
                )),
                new OpeningHoursRange("saturday", "saturday", List.of(
                        new LocalTimeRange(LocalTime.of(18, 0), LocalTime.of(0, 0))
                )),
                new OpeningHoursRange("sunday", "sunday", List.of(
                        new LocalTimeRange(LocalTime.of(11, 30), LocalTime.of(15, 0))
                ))
        );
    }
}
