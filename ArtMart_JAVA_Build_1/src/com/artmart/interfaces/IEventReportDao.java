package com.artmart.interfaces;

import com.artmart.models.EventReport;
import java.util.List;

public interface IEventReportDao {

    int createEventReport(EventReport eventReport);

    EventReport getEventReport(int eventReportID);

    List<EventReport> getAllEventReports();

    boolean updateEventReport(int eventReportID,EventReport eventReport);

    boolean deleteEventReport(int eventReportID);
    
    List<EventReport> getAllReportsByID(int userID);

}
