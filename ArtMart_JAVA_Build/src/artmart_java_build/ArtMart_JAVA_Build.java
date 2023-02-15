package artmart_java_build;

import java.sql.Date;
import java.sql.Connection;
import com.artmart.connectors.SQLConnection;
import com.artmart.models.Admin;
import java.sql.SQLException;
import com.artmart.services.UserService;


public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        try {
            Connection con = SQLConnection.getInstance().getConnection();
            Date d = Date.valueOf("2001-07-05");
            
            System.out.println("successfully connected ");
            Admin user = new Admin();
                  UserService user_ser= new UserService();
            user.setName("meher");
            user.setEmail("me.mee@example.com");
            user.setBirthday(d);
            user.setPhone_nbr(12344448);
            user.setUsername("mimy22");
            user.setPwd("pwd");
            user.setRole("client");
            user.setDepartment("1");
            //user.setBio("hahahahahah");
            user_ser.createAccountA(user);//(success)
            //user_ser.deleteAccountAr(12);  // (success)
            //user_ser.updateAccountAr(14,user);*/
            /*user = user_ser.getAdmin(22);
            if (user != null) { 
                System.out.println(user);
            }*/
        } catch (SQLException e) {
            System.err.println(e.fillInStackTrace());
            System.out.println("connection failed ");
        }
    }
}
