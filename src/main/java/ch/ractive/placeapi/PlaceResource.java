package ch.ractive.placeapi;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.ractive.placeapi.LocalEntry.OpeningHours.OpenRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceResource {
    private final LocalEntryClient localEntryClient;

    private static DayOfWeek dayOfWeek(String dayOfWeek) {
        return Arrays.stream(DayOfWeek.values())
            .filter(value -> value.name().toLowerCase().equals(dayOfWeek))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No DayOfWeek found for: " + dayOfWeek));
    }

    @RequestMapping("/{placeId}")
    public PlaceRepresentation getPlaceById(@PathVariable("placeId") String placeId) {
        return convert(localEntryClient.getLocalEntryById(placeId));
    }

    protected static PlaceRepresentation convert(LocalEntry localEntry) {
        TreeMap<String, List<OpenRange>> sortedDays = new TreeMap<>(Comparator.comparing(k -> dayOfWeek(k).getValue()));
        sortedDays.putAll(localEntry.getOpeningHours().getDays());
        // All weekdays should have a value
        Arrays.stream(DayOfWeek.values())
            .forEach(value -> sortedDays.putIfAbsent(value.name().toLowerCase(), List.of()));

        List<PlaceRepresentation.OpeningHoursRange> openingHoursRanges = new ArrayList<>();

        String weekdayStart = null;
        String weekdayEnd = null;
        List<LocalEntry.OpeningHours.OpenRange> lastOpenRanges = null;

        for (var openRangeEntry : sortedDays.entrySet()) {
            var weekday = openRangeEntry.getKey();
            var openRanges = openRangeEntry.getValue();

            if (openRanges.equals(lastOpenRanges)) {
                weekdayEnd = weekday;
            } else {
                if (weekdayStart != null) {
                    openingHoursRanges.add(createOpeningHoursRange(weekdayStart, weekdayEnd, lastOpenRanges));
                }
                weekdayStart = weekday;
                weekdayEnd = weekday;
                lastOpenRanges = openRanges;
            }
        }

        openingHoursRanges.add(createOpeningHoursRange(weekdayStart, weekdayEnd, lastOpenRanges));

        PlaceRepresentation placeRepresentation = new PlaceRepresentation(
                localEntry.getDisplayedWhat(),
                localEntry.getDisplayedWhere(),
                openingHoursRanges
        );

        log.info("LocalEntry: {}", localEntry);
        return placeRepresentation;
    }

    private static PlaceRepresentation.OpeningHoursRange createOpeningHoursRange(String weekdayStart, String weekdayEnd, List<OpenRange> lastOpenRange) {
        return new PlaceRepresentation.OpeningHoursRange(
                weekdayStart,
                weekdayEnd,
				lastOpenRange == null ? Collections.emptyList() : lastOpenRange.stream()
                    .map(range -> new PlaceRepresentation.OpeningHoursRange.LocalTimeRange(
                            range.getStart(),
                            range.getEnd())
                    )
                    .collect(Collectors.toList())
        );
    }
}
