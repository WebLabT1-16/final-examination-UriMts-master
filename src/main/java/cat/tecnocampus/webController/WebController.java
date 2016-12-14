package cat.tecnocampus.webController;

import cat.tecnocampus.domain.FavoriteJourney;
import cat.tecnocampus.domainController.FgcController;
import cat.tecnocampus.exception.UserDoesNotExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by roure on 14/11/2016.
 */
@Controller
public class WebController {

    private FgcController fgcController;

    public WebController(FgcController fgcController) {
        this.fgcController = fgcController;
    }

    @GetMapping("/stations")
    public String getStations(Model model) {

        model.addAttribute("stationList", fgcController.getStationsFromRepository());

        return "stations";
    }

    /*
    TODO 4 GET : This method should return a form in order to add a new favorite path to the user. Complete the code so
    that the model has an attribute "username" with the given username and another
    attribute called "stationList" with the list of the stations. Your should get the station list from the fgcController
    Note that:
        + In the test in test/java/cat.tecnocampus/webController/getAddMessiFavoriteJourneys you'll be able to see
        what method you need to use from the fgcController (see todoo 4.1)
     POSTCONDITION: the test getAddMessiFavoriteJourneys should pass
     */
    @GetMapping("/users/{username}/add/favoriteJourney")
    public String getAddFavoriteJourney(@PathVariable String username, Model model) {
        errors(username);

        return "newFavoriteJourney";
    }

    @PostMapping("/users/{username}/add/favoriteJourney")
    public String postAddFavoriteJourney(@PathVariable String username, FavoriteJourney favoriteJourney, Model model) {
        errors(username);

        fgcController.addUserFavoriteJourney(username, favoriteJourney);
        model.addAttribute("favoriteJourney", favoriteJourney);

        return "redirect:/users/{username}/favoriteJourneys";
    }

    /*
    TODO 3 ERROR HANDLING : code a new method/class so that the following method goes to the url "/error/userDoesNotExist" when
    the username doesn't exist in the database
    Note that:
        + This method first calls checkUserExists that throws a UserDoesNotExistsException if the user doesn't exist
        + See that the error page expects to have a "username" attribute in the model
        + There are other two methods (getAddFavoriteJourney, postAddFavoriteJourney) that also use checkUserExists and
        that should also go to the error page if the user doesn't exist. That must happen with no additional lines of code
        + It may be worth reading the tests (see todoo 3.1)
        + You must create a new method or class in the place you consider is best
    POSTCONDITION:
        + After completing the code the test in test/java/cat.tecnocampus/webControllerTest/getUnknownFavoriteJourneyTest
        should pass
        + Also tests getAddUnknownFavoriteJourneys and postAddUnknownFavoriteJourneys should pass
     */
    @GetMapping("/users/{username}/favoriteJourneys")
    public String getFavoriteJourneys(@PathVariable String username, Model model) {
        errors(username);

        model.addAttribute("username", username);
        model.addAttribute("favoriteJourneys", fgcController.getFavoriteJourneys(username));
        return "favoriteJourneys";
    }



    @GetMapping("/byebye")
    public String byebye() {

        return "byebye";
    }

    private void errors(String username){
        try{
            checkUserExists(username);
        }catch (UserDoesNotExistsException ex){
            UserDoesNotExistsException exc = new UserDoesNotExistsException(username);
            ex.setUsername(username);
            throw exc;
        }

    }

    private void checkUserExists(@PathVariable String username) throws UserDoesNotExistsException{
        if (!fgcController.existsUser(username)) {
            UserDoesNotExistsException e = new UserDoesNotExistsException("Non existing resource");
            e.setUsername(username);
            throw e;
        }
    }
}
