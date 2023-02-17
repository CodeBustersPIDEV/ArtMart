package artmart_java_build;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class ArtMart_JAVA_Build extends Application {
    
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Main_View.fxml"));
        Parent page = loader.load();
        Scene scene = new Scene(page);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
