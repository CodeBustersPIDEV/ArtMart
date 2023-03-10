package com.artmart.GUI.controllers.Event.Artist.Feedback;

import com.artmart.GUI.controllers.Product.ArtistReadyProductsListController;
import com.artmart.GUI.controllers.User.ProfileAdminController;
import com.artmart.GUI.controllers.User.ProfileArtistController;
import com.artmart.GUI.controllers.User.ProfileClientController;
import com.artmart.models.Event;
import com.artmart.models.Feedback;
import com.artmart.models.Session;
import com.artmart.models.User;
import com.artmart.services.FeedbackService;
import com.artmart.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ListFeedbackController implements Initializable {

    @FXML
    private ScrollPane scrollPaneFeedbackList;
    @FXML
    private VBox vBox;
    @FXML
    private Text txtEventName;

    private Event event = new Event();
    private List<Feedback> feedbackList;

    private FeedbackService fs = new FeedbackService();
    @FXML
    private Button btnReturn;
    @FXML
    private ChoiceBox<String> profileChoiceBox;
    @FXML
    private javafx.scene.control.Label username;
    UserService user_ser = new UserService();
    private final Session session = Session.getInstance();
    private final int userID = session.getCurrentUserId(session.getSessionId());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.makeList();
        } catch (SQLException e) {
        }
    }

    public void makeList() throws SQLException {
        this.feedbackList = this.fs.getAllFeedbacks();

        this.vBox.getChildren().clear();
        this.feedbackList.forEach(feedback -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/artmart/GUI/views/Event/Artist/Feedback/card_feedback.fxml"));
                Parent root = loader.load();
                CardFeedbackController controller = loader.getController();
                controller.setUpFeedbackData(feedback, this);
                root.setId("" + feedback.getFeedbackID());
                this.vBox.getChildren().add(root);
            } catch (IOException e) {
                System.out.print(e.getCause());
            }
        });
        // Wrap the VBox in a ScrollPane to make it scrollable
        this.scrollPaneFeedbackList = new ScrollPane(this.vBox);
        this.scrollPaneFeedbackList.setFitToWidth(true);
        this.scrollPaneFeedbackList.setFitToHeight(true);
        this.scrollPaneFeedbackList.setContent(this.vBox);
    }

    @FXML
    private void onReturn(ActionEvent event) {
        try {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Event/Artist/home_artist.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
