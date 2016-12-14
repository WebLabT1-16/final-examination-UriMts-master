package cat.tecnocampus.domainController;

import cat.tecnocampus.domain.FavoriteJourney;
import cat.tecnocampus.domain.Station;
import cat.tecnocampus.domain.User;
import cat.tecnocampus.exception.UserDoesNotExistsException;
import cat.tecnocampus.repositories.FavoriteJourneyRepository;
import cat.tecnocampus.repositories.StationRepository;
import cat.tecnocampus.repositories.UserRepository;
import cat.tecnocampus.services.LaPoblaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by roure on 07/12/2016.
 */
@Service
public class FgcController {
    private StationRepository stationRepository;
    private UserRepository userRepository;
    private LaPoblaService laPoblaService;
    private FavoriteJourneyRepository favoriteJourneyRepository;

    public FgcController(StationRepository stationRepository, UserRepository userRepository, LaPoblaService laPoblaService,
                         FavoriteJourneyRepository favoriteJourneyRepository) {
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
        this.laPoblaService = laPoblaService;
        this.favoriteJourneyRepository = favoriteJourneyRepository;
    }

    public List<Station> getStationsFromApi () {
        return laPoblaService.getLaPoblaStations();
    }

    public List<Station> getStationsFromRepository() {
        return stationRepository.findAll();
    }

    public void saveStations(List<Station> stations) {
        stationRepository.saveStations(stations);
    }

    public User getUser(String username) {
        return userRepository.findOneUser(username);
    }

    public boolean existsUser (String username) {
        return userRepository.existsUser(username);
    }

    public void addUserFavoriteJourney(String username, FavoriteJourney favoriteJourney) {
        if (existsUser(username)) {
            favoriteJourneyRepository.saveFavoriteJourney(favoriteJourney, username);
        }
        else {
            UserDoesNotExistsException e = new UserDoesNotExistsException("Non existing resource");
            e.setUsername(username);
            throw e;
        }
    }

    public List<FavoriteJourney> getFavoriteJourneys(String username) {
        if (!existsUser(username)) {
            UserDoesNotExistsException e = new UserDoesNotExistsException("Non existing resource");
            e.setUsername(username);
            throw e;
        }

        return favoriteJourneyRepository.findFavoriteJourneys(username);
    }
}
