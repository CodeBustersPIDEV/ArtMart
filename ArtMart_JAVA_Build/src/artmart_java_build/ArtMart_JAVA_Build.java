package artmart_java_build;

import com.artmart.connectors.SQLConnection;
import java.sql.SQLException;
import java.sql.Connection;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        // TODO code application logic here
        try{
            Connection con = SQLConnection.getInstance().getConnection();
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
        }
    }
}
