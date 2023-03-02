package com.artmart.interfaces;

import com.artmart.models.Activity;
import java.util.List;

public interface IActivityDao {

    int createActivity(Activity activity);

    Activity getActivity(int activityID);

    List<Activity> getAllActivities();

    boolean updateActivity(int activityID, Activity activity);

    boolean deleteActivity(int activityID);
    
    List<Activity> searchActivityByTitle(String title, int userID);
    
    List<Activity> getAllActivitiesByID(int userID);

}
