package com.artmart.interfaces;

import com.artmart.models.Chat;
import java.util.List;



public interface IChatService {

List<Chat> getAllChats();
    Chat getChatById(int id);
    int createChat(Chat chat);
    int updateChat(Chat chat);
    int deleteChat(int id);
}
