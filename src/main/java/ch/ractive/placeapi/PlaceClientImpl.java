package ch.ractive.placeapi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlaceClientImpl implements PlaceClient {
    private final RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @Value("${localentry.uritemplate}")
    private UriTemplate localEntryUriTemplate;

    @PostConstruct
    public void init() {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public LocalEntry getLocalEntryById(String id) {
        try {
            return restTemplate.getForObject(localEntryUriTemplate.expand(id), LocalEntry.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException(id, e);
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("Could not get LocalEntry: " + e.getMessage(), e);
        }
    }
}
