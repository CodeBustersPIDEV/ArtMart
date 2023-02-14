/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artmart.interfaces;

import com.artmart.models.Activity;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public interface IActivityDao {
    
    int createActivity(Activity activity);
    Activity getActivity(int activityID);
    List<Activity> getAllActivities();
    boolean updateActivity(Activity activity);
    boolean deleteActivity(int activityID);
    
}
