/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.services;

import com.artmart.dao.FeedbackDao;
import com.artmart.interfaces.IFeedbackDao;
import com.artmart.models.Feedback;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public class FeedbackService implements IFeedbackDao{
    
    private FeedbackDao dao = new FeedbackDao();

    @Override
    public int createFeedback(Feedback feedback) {
        return dao.createFeedback(feedback);
    }

    @Override
    public Feedback getFeedback(int feedbackID) {
        return dao.getFeedback(feedbackID);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return dao.getAllFeedbacks();
    }

    @Override
    public boolean updateFeedback(int feedbackID, Feedback feedback) {
        return dao.updateFeedback(feedbackID, feedback);
    }

    @Override
    public boolean deleteFeedback(int feedbackID) {
        return dao.deleteFeedback(feedbackID);
    }
    
}
