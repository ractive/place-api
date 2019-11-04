package ch.ractive.placeapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceResource {
    private final PlaceClient placeClient;

    @RequestMapping("/{placeId}")
    public PlaceRepresentation getPlaceById(@PathVariable("placeId") String placeId) {
        return convert(placeClient.getLocalEntryById(placeId));
    }

    private PlaceRepresentation convert(LocalEntry localEntry) {
        PlaceRepresentation placeRepresentation = new PlaceRepresentation(
                localEntry.getDisplayedWhat(),
                localEntry.getDisplayedWhere()
        );
        log.info("LocalEntry: {}", localEntry);
        return placeRepresentation;
    }
}
