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

        try {
            Connection con = SQLConnection.getInstance().getConnection();
            System.out.println("successfully connected ");
//System.out.println(z.getAllCategories());
//System.out.println(cc.getCustomProductById(2));
//System.out.println(y.getAllChats());
            cc.createCustomProduct(new Product(1, "amir", "soltani", "Test", 2, "Test", "Test"));
//x.updateProduct(9, new Product(1, "amwaaaaaaaaaaaaaair", "haaaaaaaaaa", "Test",2, "Test", "Test"));
//cc.deleteCustomProduct(21);
        } catch (SQLException e) {
            System.err.println(e.fillInStackTrace());
            System.out.println("connection failed ");
        }
    }
}
