/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artmart.GUI.controllers.Blog;

import com.artmart.dao.UserDao;
import com.artmart.models.Blog;
import com.artmart.models.User;
import com.artmart.services.BlogService;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Blog> blogList = new ArrayList<>();
        blogList = this.blogService.getAllBlogs();
        Map<String, Integer> blogViewsPerUser = new HashMap<>();
        blogList.forEach(blog->{
        blogViewsPerUser.put(this.userService.getUser(blog.getAuthor()).getName() , blog.getNb_views());
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

}
