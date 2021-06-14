package sample.controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.WriterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.classes.QRCode.CodeGenerator;
import sample.classes.QRCode.ImageViewCamScanner;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class WebQRController implements Initializable {

    @FXML private Button btnGenerate;
    @FXML private ChoiceBox<String> choiceBox;
    @FXML private ImageView imageView;
    @FXML private TextField txtBoxScanned;
    @FXML private ImageView imgQRView;
    @FXML private Label status;

    private ImageViewCamScanner webcam;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var cam = Webcam.getDefault();

        for (var cams : Webcam.getWebcams())
            choiceBox.getItems().add(cams.toString());

        choiceBox.setValue(Webcam.getDefault().toString());
        choiceBox.setDisable(true);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((arg, oldV, newV) -> {
            webcam.setWebcam(Webcam.getWebcams().get((Integer) newV));
        });

        webcam = new ImageViewCamScanner(cam, imageView, txtBoxScanned);
        webcam.start();
    }

    @FXML
    void btnGenerateOnClicked(MouseEvent mouseEvent) {
        var data = String.valueOf(System.currentTimeMillis());
        BufferedImage image = null;
        try {
            image = CodeGenerator.generateQRImage(data, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imgQRView.setImage(SwingFXUtils.toFXImage(image, null));
    }
}
