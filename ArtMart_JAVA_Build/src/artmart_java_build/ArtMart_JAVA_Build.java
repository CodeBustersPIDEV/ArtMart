package artmart_java_build;

import java.sql.Connection;
import com.artmart.connectors.SQLConnection;
import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ChatDao;
import com.artmart.dao.CustomProductDao;
import com.artmart.dao.ProductDao;
import com.artmart.models.Categories;
import com.artmart.models.Chat;
import com.artmart.models.CustomProduct;
import com.artmart.models.Event;
import com.artmart.models.Product;
import com.artmart.services.EventService;
import java.sql.Date;
import java.sql.SQLException;


public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        CustomProductDao cc = new CustomProductDao();
        ProductDao x = new ProductDao();
        ChatDao y = new ChatDao();
        CategoriesDao z = new CategoriesDao();
        EventService w = new EventService();
        
     
        try{
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");
           // w.createEvent( new Event("wa", "wa", "wa", "wa", 0, 0, new Date(01/01/2001), new Date(01/01/2001), 1));
            w.updateEvent(2, new Event("wa33333333333333333333333", "wa", "wa", "wa", 0, 0,Date.valueOf("2001-01-01"),Date.valueOf("2001-01-01"), 1));
//System.out.println(z.getAllCategories());
//System.out.println(cc.getCustomProductById(2));
//System.out.println(y.getAllChats());
    //  cc.createCustomProduct(new Product(1, "amir", "soltani", "Test",2, "Test", "Test"));
//x.updateProduct(9, new Product(1, "amwaaaaaaaaaaaaaair", "haaaaaaaaaa", "Test",2, "Test", "Test"));
cc.deleteCustomProduct(21);
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}