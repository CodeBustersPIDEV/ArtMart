/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.services.BlogService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author marwen
 */
public class BlogStatController implements Initializable {

    @FXML
    private BarChart<String, Integer> blogChart;
    private final BlogService blogService = new BlogService();
    private final UserDao userService = new UserDao();
    @FXML
    private Button goBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Blog> blogList = new ArrayList<>();
        blogList = this.blogService.getAllBlogsOrderedByViews();
        Map<String, Integer> blogViewsPerUser = new HashMap<>();
        blogList.forEach(blog -> {
            blogViewsPerUser.put(this.userService.getUser(blog.getAuthor()).getName(), blog.getNb_views());
        });

        // Create the chart object
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Define the axes
        xAxis.setLabel("User");
        yAxis.setLabel("Views");

        // Create the chart series
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : blogViewsPerUser.entrySet()) {
            data.add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        series.setData(data);

        // Add the chart series to the chart
        this.blogChart.getData().add(series);

    }

    @FXML
    private void goBackToBlogMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/artmart/GUI/views/Blog/BlogMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
