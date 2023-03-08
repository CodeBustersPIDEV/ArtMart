/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.artmart.interfaces;

import com.artmart.models.Participation;
import java.util.List;

public interface IParticipationDao {
    
    int createParticipation(Participation participation);

    Participation getParticipation(int participationID);

    List<Participation> getAllParticipations();

    boolean updateParticipation(int participationID, Participation participation);

    boolean deleteParticipation(int participationID);    
    
    Participation getParticipationByID(int userID, int eventID);
}
