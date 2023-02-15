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
import com.artmart.models.Product;
import java.sql.SQLException;

public class ArtMart_JAVA_Build {

    public static void main(String[] args) {
        CustomProductDao cc = new CustomProductDao();
        ProductDao x = new ProductDao();
        ChatDao y = new ChatDao();
        CategoriesDao z = new CategoriesDao();
        // TODO code application logic here
        try{
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");

            


 z.createCategories(new Categories("ahaaa"));
System.out.println(z.getAllCategories());
System.out.println(cc.getCustomProductById(2));
//y.createChat(new Chat(1, 2, 1, "aaaaaaa"));
//y.deleteChat(6);

//y.updateChat(5, new Chat(5,1, 10, 1, "eeeeeeeeeeeeeeeeeeeeeeeeee"));
System.out.println(y.getAllChats());
       //   x.createProduct(new Product(1, "amir", "soltani", "Test",2, "Test", "Test"));
      // cc.createCustomProduct(new CustomProduct(3,1, "amir", "soltani", "Test",2, "Test", "Test"));
//            System.out.println(cc.getCustomProductById(2));
//            cc.createCustomProduct(new CustomProduct(1,1,1, "Test2", "Test2", "Test2",2, "Test2", "Test2"));
//            cc.updateCustomProduct(3,new CustomProduct(1,2,1, "Test23", "Test2", "Test2",2, "Test2", "Test2"));
//            cc.deleteCustomProduct(4);
        }catch(SQLException e){
            System.err.println(e.fillInStackTrace());
             System.out.println("connection failed ");
        }
    }
}