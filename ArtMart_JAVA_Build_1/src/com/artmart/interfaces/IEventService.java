package com.artmart.interfaces;

import com.artmart.models.Activity;
import com.artmart.models.Event;
import com.artmart.models.EventReport;
import com.artmart.models.Feedback;
import java.util.List;

public interface IEventService {

    int createEvent(Event event);

    Event getEvent(int eventID);

    List<Event> getAllEvents();

    boolean updateEvent(int id,Event event);

    boolean deleteEvent(int eventID);

    int createActivity(Activity activity);

    Activity getActivity(int activityID);

    Activity getAllActivities(int activityID);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(int activityID);

    int createEventReport(EventReport eventReport);

    EventReport getEventReport(int reportID);

    EventReport getAllEventReports(int reportID);

    boolean updateEventReport(EventReport eventReport);

    boolean deleteEventReport(int reportID);

    int createFeedback(Feedback feedback);

    Feedback getFeedback(int feedbackID);

    Feedback getAllFeedbacks(int feedbackID);

    boolean updateFeedback(Feedback feedback);

    boolean deleteFeedback(int feedbackID);
}
