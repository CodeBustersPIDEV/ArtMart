
package com.artmart.models;

public class Chat {

    int chat_ID;
    private int client_ID;
    private int custom_product_ID;
    private int artist_ID;
    private String history;

    public Chat() {
    }

    public Chat(int chat_ID) {
        this.chat_ID = chat_ID;
    }

    public Chat(int client_ID, int custom_product_ID, int artist_ID, String history) {
        this.client_ID = client_ID;
        this.custom_product_ID = custom_product_ID;
        this.artist_ID = artist_ID;
        this.history = history;
    }

    public Chat(int chat_ID, int client_ID, int custom_product_ID, int artist_ID, String history) {
        this.chat_ID = chat_ID;
        this.client_ID = client_ID;
        this.custom_product_ID = custom_product_ID;
        this.artist_ID = artist_ID;
        this.history = history;
    }

    public int getChat_ID() {
        return chat_ID;
    }

    public int getClient_ID() {
        return client_ID;
    }

    public int getCustom_product_ID() {
        return custom_product_ID;
    }

    public int getArtist_ID() {
        return artist_ID;
    }

    public String getHistory() {
        return history;
    }

    public void setChat_ID(int chat_ID) {
        this.chat_ID = chat_ID;
    }

    public void setClient_ID(int client_ID) {
        this.client_ID = client_ID;
    }

    public void setCustom_product_ID(int custom_product_ID) {
        this.custom_product_ID = custom_product_ID;
    }

    public void setArtist_ID(int artist_ID) {
        this.artist_ID = artist_ID;
    }

    public void setHistory(String history) {
        this.history = history;
    }
    @Override
    public String toString() {
    return "Chat [chat_ID=" + chat_ID + "\n client_ID=" + client_ID + "\ncustom_product_ID=" + custom_product_ID
            + "\n artist_ID=" + artist_ID + "\n history=" + history + "\n********************************************"+ "]";
}

   

    
}
