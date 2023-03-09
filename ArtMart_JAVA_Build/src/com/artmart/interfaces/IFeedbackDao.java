package com.artmart.interfaces;

import com.artmart.models.Feedback;
import java.util.List;

public interface IFeedbackDao {

    int createFeedback(Feedback feedback);

    Feedback getFeedback(int feedbackID);

    List<Feedback> getAllFeedbacks();

    boolean updateFeedback(int feedbackID, Feedback feedback);

    boolean deleteFeedback(int feedbackID);
    
    List<Feedback> getAllFeedbacksByID(int eventID);
}
