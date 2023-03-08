package com.artmart.services;

import com.artmart.dao.ReadyProductDao;
import com.artmart.dao.ProductReviewDao;
import com.artmart.interfaces.IProductReviewDao;
import com.artmart.interfaces.IReadyProductDao;
import com.artmart.models.ReadyProduct;
import com.artmart.models.ProductReview;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ReadyProductService implements IReadyProductDao, IProductReviewDao {

    private final ReadyProductDao readyProductDao = new ReadyProductDao();
    private final ProductReviewDao productReviewDao = new ProductReviewDao();

    @Override
    public List<ReadyProduct> getAllReadyProducts(int uID) throws SQLException {
        return readyProductDao.getAllReadyProducts(uID);
    }
    
    @Override
    public List<ReadyProduct> getAllReadyProducts() throws SQLException {
        return readyProductDao.getAllReadyProducts();
    }

    public int getReadyProductId(int id, int uID) throws SQLException {
        return readyProductDao.getReadyProductId(id, uID);
    }
    
    public int getReadyProductId(int id) throws SQLException {
        return readyProductDao.getReadyProductId(id);
    }

    public List<ReadyProduct> searchReadyProductByName(String name) throws SQLException {
        return readyProductDao.searchReadyProductByName(name);
    }

    @Override
    public ReadyProduct getReadyProductById(int id, int uID) throws SQLException {
        return readyProductDao.getReadyProductById(id, uID);
    }

    @Override
    public int createReadyProduct(ReadyProduct readyProduct) throws SQLException {
        return readyProductDao.createReadyProduct(readyProduct);
    }

    @Override
    public boolean updateReadyProduct(int id, ReadyProduct readyProduct) throws SQLException {
        return readyProductDao.updateReadyProduct(id, readyProduct);
    }

    @Override
    public int deleteReadyProduct(int id) throws SQLException {
        return readyProductDao.deleteReadyProduct(id);
    }

    public String generateVerificationEmail(String recipientEmail, String verificationCode) {
        String emailContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>Verify your products list</title>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; }"
                + "h1 { color: #333; }"
                + ".button { background-color: #4CAF50; border: none; color: white; padding: 12px 24px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border-radius: 5px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>Your products list has been updated</h1>"
                + "<p>Dear " + recipientEmail + ",</p>"
                + "<p>The admin has added a new product for you, click the button bellow to verify the action:</p>"
                + "<p><a href='http://localhost/artmart/Verify.jsp?&token=" + verificationCode + "&email=" + recipientEmail + "' class='button'>Verify products list</a></p>"
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
                return new PasswordAuthentication("rima.essaidi@esprit.tn", "223JFT3339");
            }
        };
        Session session = Session.getInstance(properties, auth);

        // Create a new email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("rima.essaidi@esprit.tn"));
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

    //ProductReview
    @Override
    public ProductReview getProductReviewById(int id) throws SQLException {
        return productReviewDao.getProductReviewById(id);
    }

    @Override
    public int getProductReviewId(int id) throws SQLException {
        return productReviewDao.getProductReviewId(id);
    }

    @Override
    public List<ProductReview> getAllProductReviews() throws SQLException {
        return productReviewDao.getAllProductReviews();
    }

    @Override
    public List<ProductReview> getAllProductReviewsByProdId(int id) throws SQLException {
        return productReviewDao.getAllProductReviewsByProdId(id);
    }

    @Override
    public int createProductReview(ProductReview productReview) throws SQLException {
        return productReviewDao.createProductReview(productReview);
    }

    @Override
    public int updateProductReview(int id, ProductReview productReview) throws SQLException {
        return productReviewDao.updateProductReview(id, productReview);
    }

    @Override
    public int deleteProductReview(int id) throws SQLException {
        return productReviewDao.deleteProductReview(id);
    }

    @Override
    public float getRatingByProductId(int id) throws SQLException {
        return productReviewDao.getRatingByProductId(id);
    }

    public List<ReadyProduct> getAllReadyProductsByCategoryName(String categoryName, int uID) throws SQLException {
        return readyProductDao.getAllReadyProductsByCategoryName(categoryName, uID);
    }
}
