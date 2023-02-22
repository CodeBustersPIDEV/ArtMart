//import com.artmart.dao.CustomProductDao;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//import javafx.collections.FXCollections;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.XYChart;
//import javax.swing.JFrame;
//
//
//public class WeightStatisticsUIController implements Initializable {
//
//    @FXML
//    private LineChart<?, ?> stat;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            CustomProductDao customProductDao = new CustomProductDao();
//            List<Integer> weights = customProductDao.getWeightsOfAllCustomProducts();
//
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            for (int i = 0; i < weights.size(); i++) {
//                dataset.addValue(weights.get(i), "Weight", Integer.toString(i+1));
//            }
//
//            JFreeChart chart = ChartFactory.createLineChart(
//                "Weights of All Custom Products",
//                "Custom Product ID",
//                "Weight",
//                dataset,
//                PlotOrientation.VERTICAL,
//                false,
//                true,
//                false
//            );
//            chart.setBackgroundPaint(Color.white);
//
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new Dimension(500, 270));
//
//            // Add the chart panel to the stat LineChart
//            stat.setCreateSymbols(false);
//            stat.getData().add(new XYChart.Series(FXCollections.observableArrayList(
//                    new XYChart.Data("Custom Product ID", chartPanel))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
