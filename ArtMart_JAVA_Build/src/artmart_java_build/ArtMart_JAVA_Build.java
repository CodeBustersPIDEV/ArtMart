package artmart_java_build;

import java.sql.Date;
import java.sql.Connection;
import com.artmart.connectors.SQLConnection;
import com.artmart.models.User;
import java.sql.SQLException;
import com.artmart.services.UserService;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");

            User user = new User();
            UserService user_ser = new UserService();
            //user.setName("ala");
            //user.setEmail("ala.ali@example.com");
            //user.setBirthday(new Date(11/07/2011));
            //user.setPhone_nbr(12344448);
            //user.setUsername("alaa123");
            //user.setPwd("pwd");
            //user.setRole("admin");
            //user_ser.createAccountU(user);//(success)
            //user_ser.deleteAccountU(1);  // (success)
            //user_ser.updateAccountU(3,user);
            /*user=user_ser.getUser(3);

            if (user != null) {
                System.out.println("User retrieved successfully");
                System.out.println("User ID: " + user.getUser_id());
                System.out.println("Name: " + user.getName());
                System.out.println("Email: " + user.getEmail());

            } else {
                System.out.println("User retrieval failed");
            }*/
        } catch (SQLException e) {
            System.err.println(e.fillInStackTrace());
            System.out.println("connection failed ");
        }
    }
}
