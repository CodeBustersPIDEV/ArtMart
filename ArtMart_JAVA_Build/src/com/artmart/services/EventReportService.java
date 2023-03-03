/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.services;

import com.artmart.dao.EventReportDao;
import com.artmart.interfaces.IEventReportDao;
import com.artmart.models.EventReport;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public class EventReportService implements IEventReportDao{

    private EventReportDao dao = new EventReportDao();
    
    @Override
    public int createEventReport(EventReport eventReport) {
        return dao.createEventReport(eventReport);
    }

    @Override
    public EventReport getEventReport(int eventReportID) {
        return dao.getEventReport(eventReportID);
    }

    @Override
    public List<EventReport> getAllEventReports() {
        return dao.getAllEventReports();
    }

    @Override
    public boolean updateEventReport(int eventReportID,EventReport eventReport) {
        return dao.updateEventReport(eventReportID, eventReport);
    }

    @Override
    public boolean deleteEventReport(int eventReportID) {
        return dao.deleteEventReport(eventReportID);
    }

    @Override
    public List<EventReport> getAllReportsByID(int userID) {
        return dao.getAllReportsByID(userID);
    }
    
}
