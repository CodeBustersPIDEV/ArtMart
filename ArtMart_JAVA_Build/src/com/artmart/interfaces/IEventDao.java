/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artmart.interfaces;

import com.artmart.models.Event;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public interface IEventDao {
    
    int createEvent(Event event);
    Event getEvent(int eventID);
    List<Event> getAllEvents();
    boolean updateEvent(Event event);
    boolean deleteEvent(int eventID);
    
}
