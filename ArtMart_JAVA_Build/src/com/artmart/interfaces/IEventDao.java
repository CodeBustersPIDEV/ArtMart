package com.artmart.interfaces;

import com.artmart.models.Event;
import java.util.List;

public interface IEventDao {

    int createEvent(Event event);

    Event getEvent(int eventID);

    List<Event> getAllEvents();

    boolean updateEvent(int eventID, Event event);

    boolean deleteEvent(int eventID);
    
    List<Event> searchEventByName(String name);

}
