/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.services;
import com.artmart.dao.ActivityDao;

import com.artmart.interfaces.IActivityDao;
import com.artmart.models.Activity;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public class ActivityService implements IActivityDao{
    private ActivityDao dao = new ActivityDao();

    @Override
    public int createActivity(Activity activity) {
        return dao.createActivity(activity);
    }

    @Override
    public Activity getActivity(int activityID) {
        return dao.getActivity(activityID);
    }

    @Override
    public List<Activity> getAllActivities() {
        return dao.getAllActivities();
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return dao.updateActivity(activity);
    }

    @Override
    public boolean deleteActivity(int activityID) {
        return dao.deleteActivity(activityID);
    }
}