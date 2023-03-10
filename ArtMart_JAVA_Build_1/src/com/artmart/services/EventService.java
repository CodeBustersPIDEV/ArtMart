package com.artmart.services;

import com.artmart.dao.EventDao;
import com.artmart.interfaces.IEventDao;
import com.artmart.models.Event;
import java.util.List;

public class EventService implements IEventDao {

    private EventDao dao = new EventDao();

    @Override
    public int createEvent(Event event) {
        return dao.createEvent(event);
    }

    @Override
    public Event getEvent(int eventID) {
        return dao.getEvent(eventID);
    }

    @Override
    public List<Event> getAllEvents() {
        return dao.getAllEvents();
    }

    @Override
    public List<Event> getMyEvents(int id) {
        return dao.getMyEvents(id);
    }

    @Override
    public List<Event> getOtherEvents(int id) {
        return dao.getOtherEvents(id);
    }

    @Override
    public boolean updateEvent(int eventID, Event event) {
        return dao.updateEvent(eventID, event);
    }

    @Override
    public boolean deleteEvent(int eventID) {
        return dao.deleteEvent(eventID);
    }
    
    @Override
    public List<Event> searchMyEventByName(String name, int userID){
        return dao.searchMyEventByName(name, userID);
    }
    
    @Override
    public List<Event> searchOtherEventByName(String name, int userID){
        return dao.searchOtherEventByName(name, userID);
    }
    
    @Override
    public Event getEventByName(String name){
        return dao.getEventByName(name);
    }
}
