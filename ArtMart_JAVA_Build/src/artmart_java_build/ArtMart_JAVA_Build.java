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
        try{
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");

            User user=new User();
         UserService user_ser= new UserService();
        user.setName("ali");
        user.setEmail("ali.ali@example.com");
        user.setBirthday(new Date(05/07/2001));
        user.setPhone_nbr(12345678);
        user.setUsername("ali123");
        user.setPwd("password");
        user.setRole("user");
       // user_ser.createAccountU(user);(success)
       // user_ser.deleteAccountU(1);   (success)
       
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}
