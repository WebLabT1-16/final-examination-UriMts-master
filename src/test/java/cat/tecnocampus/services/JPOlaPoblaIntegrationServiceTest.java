package cat.tecnocampus.services;

import cat.tecnocampus.domain.Station;
import cat.tecnocampus.services.LaPoblaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by roure on 30/11/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JPOlaPoblaIntegrationServiceTest {
    @Autowired
    LaPoblaService laPoblaService;

    @Test
    public void serviceIntegrationTest() {
        List<Station> returnedStations = laPoblaService.getLaPoblaStations();
        assertEquals(17, returnedStations.size());
        assertEquals("Alcoletge", returnedStations.get(0).getNom());
        assertEquals("Vilanova de la Barca", returnedStations.get(1).getNom());

    }

}
