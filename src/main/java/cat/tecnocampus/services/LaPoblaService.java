package cat.tecnocampus.services;

import cat.tecnocampus.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class LaPoblaService {
    //variables privadas
    private static final String URL = "http://fcghackappbackend-env.eu-central-1.elasticbeanstalk.com/stations";
    private final RestTemplate restTemplate;


    //constructor
    public LaPoblaService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /*
    TODO 2 - REST client: complete the code so that it returns a list of stations got from an API REST
    Note that:
        + There is a defined URL string with the url you have to query. See few lines above
        + The classes HelperJTO and StationsListJTO are already defined so that the Jackson library automatically
        marshals the JSON into a HelperJTO object
        + The method getStations() (HelperJTO class) to obtain a list of stations once the query to the API REST is
        performed
    OPTIONAL: you can see the returned JSON of the API REST entering the URL to the browser. Then you'll be able to see how
    the HelperJTO and StationListJTO attributes relate with the API's returned JSON
    POSTCONDITION:
        + after completing the code the test in test.java.cat.tecnocmapus.services.JPOlaPoblaIntegrationServiceTest should work
        + Todo 2.1 after completing the code you SHOULD comment the insert statements in file data.sql that insert the stations into the database.
        Note that now the application reads the stations from the API and store them in the database.
        + If you commented the code and if your code is working the following url in the browse should return a list of stations
        http://localhost:8080/stations
     */


    public List<Station> getLaPoblaStations() {
        //variables internas
        HelperJTO stations = null;

        stations = restTemplate.getForObject(URL, HelperJTO.class);

        return (stations==null)? null : stations.getStations();
    }
}
