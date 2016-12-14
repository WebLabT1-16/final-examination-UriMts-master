package cat.tecnocampus.domain;

/**
 * Created by roure on 14/11/2016.
 */
public class Journey {

    private long id;

    private Station origin;

    private Station destination;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", origin=" + origin +
                ", destination=" + destination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (origin != null ? !origin.equals(journey.origin) : journey.origin != null) return false;
        return destination != null ? destination.equals(journey.destination) : journey.destination == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }
}
