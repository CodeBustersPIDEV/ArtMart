package com.artmart.services;

import com.artmart.interfaces.IUserService;
import com.artmart.models.Admin;
import com.artmart.models.Artist;
import com.artmart.models.Client;
import com.artmart.models.User;
import java.util.List;
import com.artmart.dao.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserService implements IUserService {

    private final UserDao userDao = new UserDao();
    private final ArtistDao artistDao = new ArtistDao();
    private final AdminDao adminDao = new AdminDao();
    private final ClientDao clientDao = new ClientDao();

    @Override
    public User getUser(int user_id) {
        return this.userDao.getUser(user_id);
    }

    @Override
    public boolean updateAccountU(int user_id, User user) {
        return this.userDao.updateAccountU(user_id, user);
    }

    @Override
    public int createAccountC(Client client) {
        return this.clientDao.createAccountC(client);
    }

    @Override
    public Client getClient(int user_id) {
        return this.clientDao.getClient(user_id);
    }

    @Override
    public boolean deleteAccountC(int user_id) {
        return this.clientDao.deleteAccountC(user_id);
    }

    @Override
    public boolean updateAccountC(int user_id, Client client) {
        return this.clientDao.updateAccountC(user_id, client);
    }

    @Override
    public int createAccountAr(Artist artist) {
        return this.artistDao.createAccountAr(artist);
    }

    @Override
    public Artist getArtist(int user_id) {
        return this.artistDao.getArtist(user_id);
    }

    @Override
    public boolean deleteAccountAr(int artist_d) {
        return this.artistDao.deleteAccountAr(artist_d);
    }

    @Override
    public boolean updateAccountAr(int user_id, Artist artist) {
        return this.artistDao.updateAccountAr(user_id, artist);
    }

    @Override
    public int createAccountA(Admin admin) {
        return this.adminDao.createAccountA(admin);
    }

    @Override
    public Admin getAdmin(int user_id) {
        return this.adminDao.getAdmin(user_id);
    }

    @Override
    public List<User> viewListOfUsers() {
        return this.userDao.viewListOfUsers();
    }

    @Override
    public boolean deleteAccountA(int user_id) {
        return this.adminDao.deleteAccountA(user_id);
    }

    @Override
    public boolean updateAccountA(int user_id, Admin admin) {
        return this.adminDao.updateAccountA(user_id, admin);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userDao.authenticate(username, password);

    }

    @Override
    public int getUserIdByUsername(String username) {
        return this.userDao.getUserIdByUsername(username);
    }

    @Override
    public int getUserIdByEmail(String email) {
        return this.userDao.getUserIdByEmail(email);
    }

    @Override
    public boolean blockUser(int user_id, boolean state) {
        return this.userDao.blockUser(user_id, state);
    }

    public String generateVerificationEmail(String recipientEmail, String verificationCode) {
        String emailContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>Verify your email address</title>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; }"
                + "h1 { color: #333; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>Verify your email address</h1>"
                + "<p>Dear " + recipientEmail + ",</p>"
                + "<p>Thank you for registering with our service. To verify your email address, please copy the token bellow:</p>"
                + "<p>" + verificationCode + "</p>"
                + "</body>"
                + "</html>";
        return emailContent;
    }

    public static void sendEmail(String recipientEmail, String emailSubject, String emailBody) throws MessagingException {
        // Configure the email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Create a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("samar.hamdi@esprit.tn", "pika05072001");
            }
        };
        Session session = Session.getInstance(properties, auth);

        // Create a new email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("samar.hamdi@esprit.tn"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(emailSubject);
        message.setContent(emailBody, "text/html");

        // Send the email message
        try {
            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Failed to send email");
            e.printStackTrace();
        }
    }

    @Override
    public void StoreToken(String token, String email) {
        this.userDao.StoreToken(token, email);
    }

    @Override
    public String verifyToken(String email) {
        return this.userDao.verifyToken(email);
    }

    @Override
    public boolean enableUser(String email) {
        return this.userDao.enableUser(email);
    }

    @Override
    public String getPhoneNumberById(int userId) {
        return this.userDao.getPhoneNumberById(userId);
    }

}
