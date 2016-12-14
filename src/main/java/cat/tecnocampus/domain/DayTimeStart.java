package cat.tecnocampus.domain;

/**
 * Created by roure on 18/11/2016.
 */
public class DayTimeStart {

    private long id;

    private String begin;

    private String dayOfWeek;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayTimeStart that = (DayTimeStart) o;

        if (begin != null ? !begin.equals(that.begin) : that.begin != null) return false;
        return dayOfWeek != null ? dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek == null;
    }

    @Override
    public int hashCode() {
        int result = begin != null ? begin.hashCode() : 0;
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        return result;
    }
}
