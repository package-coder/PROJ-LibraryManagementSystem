package sample.classes.QRCode;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.NotFoundException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;

public class ImageViewCamScanner extends WebCamScanner{
    private final ImageView imageView;
    private final TextField txtBoxScanned;

    public ImageViewCamScanner(Webcam webcam, ImageView imageView, TextField txtBoxScanned) {
        super(webcam);
        this.imageView = imageView;
        this.txtBoxScanned = txtBoxScanned;
    }

    @Override
    public void elapsedImage(BufferedImage image) {
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
        try {
            txtBoxScanned.setText(CodeDecoder.decode(image));
        } catch (NotFoundException e) {
            txtBoxScanned.setText("");
        }
    }
}
