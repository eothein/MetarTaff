package be.hogent.jensbuysse.metartaff.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.relation.ToOne;

/**
 * Created by eothein on 07.11.17.
 */


@Entity
public class Metar {

    @Id
    private long id;

    /**
     * raw MetarCode
     */
    private String rawMetar;

    /**
     * Aiportcode folloing the ICA standard
     */
    @Index
    private ToOne<Aiport> airportCode;

    /**
     * Day of the month
     */
    private int dayOfMonth;

    /**
     * Time
     */
    private int time;

    /**
     * Direction of the wind
     */
    private int windDirection;

    /**
     * The speed of the wind
     */
    private int windSpeed;


    /**
     * Possible gusts
     */
    private int uitSchieters;

    /**
     * Line of sight
     */
    private int sight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRawMetar() {
        return rawMetar;
    }

    public void setRawMetar(String rawMetar) {
        this.rawMetar = rawMetar;
    }

    public ToOne<Aiport> getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(ToOne<Aiport> airportCode) {
        this.airportCode = airportCode;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getUitSchieters() {
        return uitSchieters;
    }

    public void setUitSchieters(int uitSchieters) {
        this.uitSchieters = uitSchieters;
    }

    public int getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }
}
