package cat.tecnocampus;

import cat.tecnocampus.domain.Station;
import cat.tecnocampus.repositories.StationRepository;
import cat.tecnocampus.services.LaPoblaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class FcGhackappBackendApplication {


	public static void main(String[] args) {
		SpringApplication.run(FcGhackappBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(LaPoblaService laPoblaService, StationRepository stationRepository) throws Exception {
		return args -> {

			//adding stations to the database
			List<Station> stations = laPoblaService.getLaPoblaStations();

			if (stations != null) {
				stations.forEach(System.out::println);

				stationRepository.saveStations(stations);
			}
		};
	}
}
