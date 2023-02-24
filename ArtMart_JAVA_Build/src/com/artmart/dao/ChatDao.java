package com.artmart.dao;
import com.artmart.connectors.SQLConnection;
import com.artmart.interfaces.IChatDao;
import com.artmart.models.Chat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ChatDao implements IChatDao {
    private final SQLConnection sqlConnection = SQLConnection.getInstance();
    @Override
    public int createChat(Chat chat) throws SQLException {
        String query = "INSERT INTO chat (client_ID, custom_product_ID, artist_ID, history) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, chat.getClient_ID());
        statement.setInt(2, chat.getCustom_product_ID());
        statement.setInt(3, chat.getArtist_ID());
        statement.setString(4, chat.getHistory());

        return statement.executeUpdate();
    }
    @Override
    public int updateChat(int id, Chat chat) throws SQLException {
        String query = "UPDATE chat SET chat_ID = ?, client_ID = ?, custom_product_ID = ?, artist_ID = ?, history = ? WHERE chat_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, chat.getChat_ID());
        statement.setInt(2, chat.getClient_ID());
        statement.setInt(3, chat.getCustom_product_ID());
        statement.setInt(4, chat.getArtist_ID());
        statement.setString(5, chat.getHistory());
        statement.setInt(6, id);
        return statement.executeUpdate();
    }

    @Override
    public int deleteChat(int id) throws SQLException {
        String query = "DELETE FROM chat WHERE chat_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeUpdate();

    }

    @Override
    public Chat getChatById(int chatId) throws SQLException {
        String query = "SELECT chat_ID, client_ID, custom_product_ID, artist_ID, history FROM chat WHERE chat_ID = ?";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        statement.setInt(1, chatId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            int clientId = result.getInt("client_ID");
            int customProductId = result.getInt("custom_product_ID");
            int artistId = result.getInt("artist_ID");
            String history = result.getString("history");
            return new Chat(chatId, clientId, customProductId, artistId, history);
        } else {
            return null;
        }
    }

    @Override
    public List<Chat> getAllChats() throws SQLException {
        List<Chat> chats = new ArrayList<>();
        String query = "SELECT chat_ID, client_ID, custom_product_ID, artist_ID, history FROM chat";
        PreparedStatement statement = sqlConnection.getConnection().prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int chatId = result.getInt("chat_ID");
            int clientId = result.getInt("client_ID");
            int customProductId = result.getInt("custom_product_ID");
            int artistId = result.getInt("artist_ID");
            String history = result.getString("history");
            Chat chat = new Chat(chatId, clientId, customProductId, artistId, history);
            chats.add(chat);
        }
        return chats;
    }
 
}