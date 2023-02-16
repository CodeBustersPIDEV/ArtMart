package com.artmart.services;

import com.artmart.models.Chat;
import com.artmart.dao.ChatDao;
import com.artmart.interfaces.IChatDao;
import java.sql.SQLException;
import java.util.List;

public class ChatService implements IChatDao {

    private ChatDao x = new ChatDao();

    @Override
    public Chat getChatById(int id) throws SQLException {
        return x.getChatById(id);
    }

    @Override
    public int createChat(Chat chat) throws SQLException {
        return x.createChat(chat);
    }

    @Override
    public int updateChat(int id, Chat chat) throws SQLException {
        return x.updateChat(id, chat);
    }

    @Override
    public int deleteChat(int id) throws SQLException {
        return x.deleteChat(id);
    }

    @Override
    public List<Chat> getAllChats() throws SQLException {
        return x.getAllChats();
    }

}
