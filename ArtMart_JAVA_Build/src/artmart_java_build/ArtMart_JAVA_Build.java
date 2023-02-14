package artmart_java_build;

import java.sql.Connection;
import com.artmart.connectors.SQLConnection;
import com.artmart.dao.CustomProductDao;
import com.artmart.models.CustomProduct;
import java.sql.SQLException;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        CustomProductDao cc = new CustomProductDao();
        // TODO code application logic here
        try{
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");
            cc.createCustomProduct(new CustomProduct(1,1,1, "Test2", "Test", "Test",2, "Test", "Test"));
            System.out.println(cc.getCustomProductById(2));
            cc.createCustomProduct(new CustomProduct(1,1,1, "Test2", "Test2", "Test2",2, "Test2", "Test2"));
            cc.updateCustomProduct(3,new CustomProduct(1,2,1, "Test23", "Test2", "Test2",2, "Test2", "Test2"));
            cc.deleteCustomProduct(4);
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}
