package ch.ractive.placeapi;

import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping("/{placeId}")
    public PlaceRepresentation getPlaceById(@PathVariable("placeId") String placeId) {
        return convert(localEntryClient.getLocalEntryById(placeId));
    }

    protected static PlaceRepresentation convert(LocalEntry localEntry) {
        var days = localEntry.getOpeningHours().getDays();

        List<PlaceRepresentation.OpeningHoursRange> openingHoursRanges = new ArrayList<>();

        String weekdayStart = null;
        String weekdayEnd = null;
        List<LocalEntry.OpeningHours.OpenRange> lastOpenRange = null;

        for (var openRangeEntry : days.entrySet()) {
            var weekday = openRangeEntry.getKey();
            var openRange = openRangeEntry.getValue();

            if (openRange.equals(lastOpenRange)) {
                weekdayEnd = weekday;
            } else {
                if (weekdayStart != null) {
                    openingHoursRanges.add(createOpeningHoursRange(weekdayStart, weekdayEnd, lastOpenRange));
                }
                weekdayStart = weekday;
                weekdayEnd = weekday;
                lastOpenRange = openRange;
            }
        }

        openingHoursRanges.add(createOpeningHoursRange(weekdayStart, weekdayEnd, lastOpenRange));

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
                lastOpenRange.stream()
                    .map(range -> new PlaceRepresentation.OpeningHoursRange.LocalTimeRange(
                            range.getStart(),
                            range.getEnd())
                    )
                    .collect(Collectors.toList())
        );
    }
}
