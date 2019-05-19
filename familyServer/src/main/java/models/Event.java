package models;

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
    private String type = null;
    private int year = 0;
    
    
    public Event(){}
    
    /**
     * Create an Event with given info
     */
    public Event(String event_id, String user_name, String person_id, float latitude,
        float longitude, String country, String city, String type, int year){
            this.event_id = event_id;
            this.user_name = user_name;
            this.person_id = person_id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.country = country;
            this.type = type;
            this.year = year;
        }
        
    public String getEventId(){return event_id;}
    public String getUserName(){return user_name;}
    public String getPersonId(){return person_id;}
    public float    getLatitude(){return latitude;}
    public float    getLongitude(){return longitude;}
    public String getCountry(){return country;}
    public String getCity(){return city;}
    public String getType(){return type;}
    public int getYear(){return year;}
}