package cat.tecnocampus.services;

import cat.tecnocampus.domain.Station;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by roure on 30/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationsListJTO {
    private List<Station> stations;

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {

        this.stations = stations.stream().distinct().collect(Collectors.toList());

    }
}
