package com.artmart.interfaces;

import com.artmart.models.Event;
import java.util.List;

public interface IEventDao {

    int createEvent(Event event);

    Event getEvent(int eventID);

    List<Event> getAllEvents();

    List<Event> getMyEvents(int id);

    List<Event> getOtherEvents(int id);

    boolean updateEvent(int eventID, Event event);

    boolean deleteEvent(int eventID);
    
    List<Event> searchMyEventByName(String name, int userID);

    List<Event> searchOtherEventByName(String name, int userID);
    
    Event getEventByName(String name);

}
