import java.util.Date;

public class Event {
    private long eventID;
    private String eventTitle;
    private String location;
    private String description;
    private Date date;

    public Event(long eventID, String eventTitle, String location, String description, Date date) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    public long getEventID() {
        return eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

