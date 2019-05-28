package models;

import java.util.Objects;
import java.util.UUID;

/**
 * Holds info for event objects.
 */
public class Event{
    
    private String event_id = null;
    private String user_name = null;
    private String person_id = null;
    private float latitude = 0;
    private float longitude = 0;
    private String country = null;
    private String city = null;
    private String event_type = null;
    private int year = 0;

    public Event(){}

    /**
     * Create an Event with given info
     */
    public Event(String user_name, String person_id, float latitude,
        float longitude, String country, String city, String type, int year){
            this.event_id = UUID.randomUUID().toString();
            this.user_name = user_name;
            this.person_id = person_id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.country = country;
            this.city = city;
            this.event_type = type;
            this.year = year;
        }

    public Event(String event_id, String user_name, String person_id, float latitude,
                 float longitude, String country, String city, String event_type, int year){
        this.event_id = event_id;
        this.user_name = user_name;
        this.person_id = person_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.event_type = event_type;
        this.year = year;
    }
    
    public String getEventId(){return event_id;}
    public String getUserName(){return user_name;}
    public String getPersonId(){return person_id;}
    public float    getLatitude(){return latitude;}
    public float    getLongitude(){return longitude;}
    public String getCountry(){return country;}
    public String getCity(){return city;}
    public String getEvent_type(){return event_type;}
    public int getYear(){return year;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Float.compare(event.getLatitude(), getLatitude()) == 0 &&
                Float.compare(event.getLongitude(), getLongitude()) == 0 &&
                getYear() == event.getYear() &&
                Objects.equals(event_id, event.event_id) &&
                Objects.equals(user_name, event.user_name) &&
                Objects.equals(person_id, event.person_id) &&
                Objects.equals(getCountry(), event.getCountry()) &&
                Objects.equals(getCity(), event.getCity()) &&
                Objects.equals(getEvent_type(), event.getEvent_type());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event_id, user_name, person_id, getLatitude(), getLongitude(), getCountry(), getCity(), getEvent_type(), getYear());
    }
}