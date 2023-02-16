package com.artmart.interfaces;

import com.artmart.models.Chat;
import java.sql.SQLException;
import java.util.List;

public interface IChatDao {

    Chat getChatById(int id) throws SQLException;

    int createChat(Chat chat) throws SQLException;

    int updateChat(int id, Chat chat) throws SQLException;

    int deleteChat(int id) throws SQLException;

    List<Chat> getAllChats() throws SQLException;

}
