package artmart_java_build;

import com.artmart.connectors.SQLConnection;
import java.sql.SQLException;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        // TODO code application logic here
        try{
            SQLConnection.getInstance().connect();
            System.out.println("successfully connected ");
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}
