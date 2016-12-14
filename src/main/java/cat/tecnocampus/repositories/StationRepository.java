package cat.tecnocampus.repositories;

import cat.tecnocampus.domain.Station;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by roure on 16/11/2016.
 */
@Repository
public class StationRepository  {

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "INSERT INTO station (longitud, latitud, nom) values(?, ?, ?)";

    public StationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /*
    Note that:
        + There is the SQL_INSERT string already defined with the sql sentence you need (see few lines above)
        + A station has only three relevant attributes. Namely: "longitud", "latitud" and "nom"
     POSTCONDITION: after completing the code the test in test/java/cat.tecnocampus/repositories should be working. It is NOT worth
     reading the code of the test (doesn't help)
     */
    public int[] saveStations(List<Station> stations) {

        // YOUR CODE HERE
        for(Station s : stations){
            this.jdbcTemplate.update(SQL_INSERT,s.getLongitud(),s.getLatitud(), s.getNom());
        }

        return null;
    }

    public List<Station> findAll() {

        return jdbcTemplate.query("SELECT * FROM STATION", new StationMapper());
    }

    public Station findOne(String nom) {
        return jdbcTemplate.queryForObject("select * from station where nom = ?", new StationMapper(), nom);
    }

    //used for testing only to leave the table as it was
    public int delete(String nom) {
        return jdbcTemplate.update("delete from station where nom like '" + nom +"%'");
    }

    public final class StationMapper implements RowMapper<Station> {
        @Override
        public Station mapRow(ResultSet resultSet, int i) throws SQLException {
            Station station = new Station();

            station.setNom(resultSet.getString("nom"));
            station.setLatitud(resultSet.getString("latitud"));
            station.setLongitud(resultSet.getString("longitud"));

            return station;
        }
    }

}
