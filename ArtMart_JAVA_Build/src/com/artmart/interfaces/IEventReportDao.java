/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artmart.interfaces;

import com.artmart.models.EventReport;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public interface IEventReportDao {
    
    int createEventReport(EventReport eventReport);
    EventReport getEventReport(int eventReportID);
    List <EventReport> getAllEventReports();
    boolean updateEventReport(EventReport eventReport);
    boolean deleteEventReport(int eventReportID);
    
}
