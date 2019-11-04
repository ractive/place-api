package ch.ractive.placeapi;

import java.util.Optional;

public interface PlaceClient {
    LocalEntry getLocalEntryById(String id);
}
