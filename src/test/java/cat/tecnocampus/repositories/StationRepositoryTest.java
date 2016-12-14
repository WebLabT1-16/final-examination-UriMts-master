package cat.tecnocampus.repositories;

import cat.tecnocampus.domain.Station;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Created by roure on 13/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StationRepositoryTest {

    @Autowired
    StationRepository stationRepository;

    @Test
    public void createStations() throws Exception{
        List<Station> stations = IntStream.range(0, 17)
                .mapToObj(i -> {Station s = new Station(); s.setNom("estacio " + i);return s;})
                .collect(Collectors.toList());

        stationRepository.saveStations(stations);
        List<Station> res = stationRepository.findAll();
        assertEquals("Expected number of stations", 34, res.size());

        stationRepository.delete("estacio");
        res = stationRepository.findAll();
        assertEquals("Expected number of stations", 17, res.size());
    }
}
