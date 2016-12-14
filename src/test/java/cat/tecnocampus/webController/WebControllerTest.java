package cat.tecnocampus.webController;

import cat.tecnocampus.domain.DayTimeStart;
import cat.tecnocampus.domain.FavoriteJourney;
import cat.tecnocampus.domain.Journey;
import cat.tecnocampus.domain.Station;
import cat.tecnocampus.domainController.FgcController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by roure on 05/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FgcController mockFgcController;

    @Test
    @WithMessiUser
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/stations"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMessiUser
    public void getStationsTest() throws Exception{
        List<Station> stations = IntStream.range(0, 17)
                .mapToObj(i -> {Station s = new Station(); s.setNom("estacio " + i);return s;})
                .collect(Collectors.toList());
        when(mockFgcController.getStationsFromRepository()).thenReturn(stations);

        mockMvc.perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(view().name("stations"))
                .andExpect(model().attributeExists("stationList"))
                .andExpect(model().attribute("stationList", hasSize(17)));

        verify(mockFgcController, times(1)).getStationsFromRepository();
        verifyNoMoreInteractions(mockFgcController);
    }

    @Test
    @WithMessiUser
    public void getMessiFavoriteJourneyTest() throws  Exception {
        when(mockFgcController.existsUser("messi")).thenReturn(true);

        List<FavoriteJourney> journeys = new ArrayList<>();
        journeys.add(createFavoriteJourney());
        when(mockFgcController.getFavoriteJourneys("messi")).thenReturn(journeys);

        mockMvc.perform(get("/users/messi/favoriteJourneys"))
                .andExpect(status().isOk())
                .andExpect(view().name("favoriteJourneys"))
                .andExpect(model().attributeExists("username", "favoriteJourneys"))
                .andExpect(model().attribute("username", is("messi")))
                .andExpect(model().attribute("favoriteJourneys", hasSize(1)))
                .andExpect(model().attribute("favoriteJourneys", hasItem(
                        allOf(
                                hasProperty("journey", hasProperty("origin", hasProperty("nom", is("Tremp")))),
                                hasProperty("journey", hasProperty("destination", hasProperty("nom", is("La Pobla")))),
                                hasProperty("startList", hasSize(1))
                        )
                )));

        verify(mockFgcController, times(1)).existsUser("messi");
        verify(mockFgcController, times(1)).getFavoriteJourneys("messi");
        verifyNoMoreInteractions(mockFgcController);
    }

    //TODO 3.1: just read the test (and the method testUnknownUser)
    @Test
    @WithMockUser(username="unknown", roles={"USER"})
    public void getUnknownFavoriteJourneyTest() throws Exception {
        testUnknownUser(get("/users/unknown/favoriteJourneys"));
    }

    //TODO 4.1: just read the test to discover the method to call
    @Test
    @WithMessiUser
    public void getAddMessiFavoriteJourneys() throws Exception{
        when(mockFgcController.existsUser("messi")).thenReturn(true);

        mockMvc.perform(get("/users/messi/add/favoriteJourney"))
                .andExpect(status().isOk())
                .andExpect(view().name("newFavoriteJourney"))
                .andExpect(model().attributeExists("username", "stationList"))
                .andExpect(model().attribute("username", is("messi")));

        verify(mockFgcController, times(1)).existsUser("messi");
        verify(mockFgcController, times(1)).getStationsFromRepository();
        verifyNoMoreInteractions(mockFgcController);
    }

    @Test
    @WithMockUser(username="unknown", roles={"USER"})
    public void getAddUnknownFavoriteJourneys() throws Exception{
        testUnknownUser(get("/users/unknown/add/favoriteJourney"));
    }

    @Test
    @WithMessiUser
    public void postAddMessiFavoriteJourneys() throws Exception{
        FavoriteJourney fj = createFavoriteJourney();
        when(mockFgcController.existsUser("messi")).thenReturn(true);

        List<FavoriteJourney> journeys = new ArrayList<>();
        journeys.add(fj);
        when(mockFgcController.getFavoriteJourneys("messi")).thenReturn(journeys);

        mockMvc.perform(post("/users/messi/add/favoriteJourney")
                .param("journey.origin.nom", "Tremp")
                .param("journey.destination.nom", "La Pobla")
                .param("startList[0].dayOfWeek", "Monday")
                .param("startList[0].begin", "12:12"))
                .andExpect(view().name("redirect:/users/{username}/favoriteJourneys"))
                .andExpect(redirectedUrl("/users/messi/favoriteJourneys"))
                .andExpect(status().isFound());

        verify(mockFgcController, times(1)).existsUser("messi");
        verify(mockFgcController, times(1)).addUserFavoriteJourney("messi", fj);
        verifyNoMoreInteractions(mockFgcController);
    }

    @Test
    @WithMockUser(username="unknown", roles={"USER"})
    public void postAddUnknownFavoriteJourneys() throws Exception {
        testUnknownUser(post("/users/unknown/add/favoriteJourney"));
    }

    @Test
    @WithAnonymousUser
    public void testWithAnonymousUser() throws Exception {
        mockMvc.perform(get("/stations"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser("ronaldo")
    public void testWithInvalidUser() throws Exception {
        mockMvc.perform(get("/users/messi/favoriteJourneys"))
                .andExpect(status().isForbidden());
    }


    private void testUnknownUser(RequestBuilder requestBuilder) throws Exception {
        when(mockFgcController.existsUser("unknown")).thenReturn(false);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(view().name("/error/userDoesNotExist"))
                .andExpect(model().attribute("username", is("unknown")));

        verify(mockFgcController, times(1)).existsUser("unknown");
        verifyNoMoreInteractions(mockFgcController);
    }

    private FavoriteJourney createFavoriteJourney () {
        Station origin = new Station();
        origin.setNom("Tremp");
        Station destination = new Station();
        destination.setNom("La Pobla");

        Journey j = new Journey();
        j.setOrigin(origin);
        j.setDestination(destination);

        ArrayList<DayTimeStart> starts = new ArrayList<>();
        DayTimeStart start = new DayTimeStart();
        start.setBegin("12:12");
        start.setDayOfWeek("Monday");
        starts.add(start);

        FavoriteJourney fj = new FavoriteJourney();
        fj.setJourney(j);
        fj.setStartList(starts);

        return fj;
    }
}
