package com.artmart.models;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private String userRole;
    private static Session instance;
    private int userId;
    private String username;
    private String sessionId;
    private String phoneNumber;
    private static final Map<String, Session> activeSessions = new HashMap<>();

    public Session() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }

    public static void logIn(String sessionId, Session session) {
        activeSessions.put(sessionId, session);
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String sessionId, String phoneNumber) {
        Session session = activeSessions.get(sessionId);
        if (session != null) {
            session.setPhoneNumber(phoneNumber);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isLoggedIn() {
        return sessionId != null;
    }

    public void logOut(String sessionId) {
        Session session = activeSessions.remove(sessionId);
    }

    public static Map<String, Session> getActiveSessions() {
        return activeSessions;
    }

    @Override
    public String toString() {
        return "Session{" + "userId=" + userId + ", username=" + username + ", sessionId=" + sessionId + '}';
    }

    public static Session getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public static int getCurrentUserId(String sessionId) {
        Session session = getSession(sessionId);
        if (session != null) {
            return session.getUserId();
        }
        return 0;
    }

    public int getUserID(String sessionId) {

        Session session = activeSessions.get(sessionId);
        if (session != null) {
            int userId = session.getUserId();
            System.out.println(userId);
            return userId;
        }
        return 0;
    }
}
