/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artmart.services;

import com.artmart.dao.ParticipationDao;
import com.artmart.interfaces.IParticipationDao;
import com.artmart.models.Participation;
import java.util.List;

/**
 *
 * @author GhassenZ
 */
public class ParticipationService implements IParticipationDao{
    
    private final ParticipationDao dao = new ParticipationDao();

    @Override
    public int createParticipation(Participation participation) {
        return dao.createParticipation(participation);
    }

    @Override
    public Participation getParticipation(int participationID) {
        return dao.getParticipation(participationID);
    }

    @Override
    public List<Participation> getAllParticipations() {
        return dao.getAllParticipations();
    }

    @Override
    public boolean updateParticipation(int participationID, Participation participation) {
        return dao.updateParticipation(participationID, participation);
    }

    @Override
    public boolean deleteParticipation(int participationID) {
        return dao.deleteParticipation(participationID);
    }

    @Override
    public Participation getParticipationByID(int userID, int eventID) {
        return dao.getParticipationByID(userID, eventID);
    }
    
}
