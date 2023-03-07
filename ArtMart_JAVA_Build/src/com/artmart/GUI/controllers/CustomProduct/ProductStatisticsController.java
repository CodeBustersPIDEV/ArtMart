package com.artmart.GUI.controllers.CustomProduct;

import com.artmart.dao.CategoriesDao;
import com.artmart.dao.ProductDao;
import com.artmart.models.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.FacebookType;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import com.restfb.Version;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import static java.util.Date.from;
import java.util.Properties;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author solta
 */
public class ProductStatisticsController implements Initializable {

    @FXML
    private StackPane chartContainer;
    private ProductDao productDao = new ProductDao();
    private CategoriesDao categoriesDao = new CategoriesDao();

    private ChartPanel weightChartPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Product> products = productDao.getAllProduct();

            // Compute statistics for weight and material
            Map<String, Float> weightStats = products.stream()
                    .collect(Collectors.groupingBy(Product::getMaterial, Collectors.summingDouble(Product::getWeight)))
                    .entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().floatValue()));

            // Create dataset for weight chart
            DefaultCategoryDataset weightDataset = new DefaultCategoryDataset();
            weightStats.forEach((material, weight) -> weightDataset.setValue(weight, "Weight", material));

            // Create chart for weight statistics
            JFreeChart weightChart = ChartFactory.createBarChart("Product Weight Statistics", "Material", "Weight", weightDataset);
            weightChartPanel = new ChartPanel(weightChart);

            // Create chart panel and add to container
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(weightChartPanel);
            chartContainer.getChildren().add(swingNode);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void print(ActionEvent event) throws FileNotFoundException, DocumentException, AWTException, MessagingException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\solta\\OneDrive\\Bureau\\pdf\\chart.pdf"));

        document.open();
        document.newPage();

        Graphics2D g2 = (Graphics2D) weightChartPanel.getGraphics();
        PdfContentByte cb = writer.getDirectContent();
        PdfTemplate tp = cb.createTemplate(weightChartPanel.getWidth(), weightChartPanel.getHeight());
        Graphics2D g2d = tp.createGraphics(weightChartPanel.getWidth(), weightChartPanel.getHeight());
        weightChartPanel.print(g2d);
        g2d.dispose();
        cb.addTemplate(tp, 0, 0);

        document.close();
        writer.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PDF Export");
        alert.setHeaderText(null);
        alert.setContentText("PDF file has been saved to the desktop.");
        alert.showAndWait();

        SystemTray tray = SystemTray.getSystemTray();

        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Custom product Statistic");

        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("Custom product Statistic");
        tray.add(trayIcon);

        trayIcon.displayMessage("your pdf is printed check your desktop ", "Custom product Statistic", MessageType.INFO);

    }

@FXML
private void qrcode(ActionEvent event) {
    try {
        // Get the PDF file URL
        String pdfUrl = "https://drive.google.com/file/d/1VTEYgW6mkyLa1AZ7HKoVIG7I8b391Yq6/view?usp=sharing";

        // Generate the QR code image using Google ZXing library
        int size = 250; // size of the QR code image
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = qrWriter.encode(pdfUrl, BarcodeFormat.QR_CODE, size, size);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);

        // Create a new JavaFX image from the QR code image
        WritableImage qrFxImage = SwingFXUtils.toFXImage(qrImage, null);

        // Create a new scene with only the QR code image
        Pane qrPane = new Pane(new ImageView(qrFxImage));
        Scene qrScene = new Scene(qrPane, size, size);

        // Create a new window to display the QR code scene
        Stage qrStage = new Stage();
        qrStage.setTitle("QR Code");
        qrStage.setScene(qrScene);
        qrStage.show();
    } catch (WriterException e) {
        // Handle QR code generation exception
        e.printStackTrace();
    }
}


}
