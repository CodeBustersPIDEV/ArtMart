/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artmart.interfaces;

import com.artmart.models.Feedback;

/**
 *
 * @author GhassenZ
 */
public interface IFeedbackDao {
    
    int createFeedback(Feedback feedback);
    Feedback getFeedback(int feedbackID);
    Feedback getAllFeedbacks(int feedbackID);
    boolean updateFeedback(Feedback feedback);
    boolean deleteFeedback(int feedbackID);
}
