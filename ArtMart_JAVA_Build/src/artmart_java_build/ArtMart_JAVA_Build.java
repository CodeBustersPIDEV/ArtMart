package artmart_java_build;

import java.sql.Connection;
import com.artmart.connectors.SQLConnection;
import java.sql.SQLException;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        // TODO code application logic here
        try{
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}
