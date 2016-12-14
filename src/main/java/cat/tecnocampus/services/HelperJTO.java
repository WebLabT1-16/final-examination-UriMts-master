package cat.tecnocampus.services;

import cat.tecnocampus.domain.Station;

import java.util.List;

/**
 * Created by roure on 30/11/2016.
 */
public class HelperJTO {
    private StationsListJTO _embedded;

    public StationsListJTO get_embedded() {
        return _embedded;
    }

    public void set_embedded(StationsListJTO _embedded) {
        this._embedded = _embedded;
    }

    public List<Station> getStations() {
        return _embedded.getStations();
    }
}
