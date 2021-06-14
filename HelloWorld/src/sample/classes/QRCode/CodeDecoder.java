package sample.classes.QRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CodeDecoder {
    private static Map getMap(){
        var map = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return map;
    }

    public static String decode(BufferedImage image) throws NotFoundException {
        var binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(image)));
        var result = new MultiFormatReader().decode(binaryBitmap, getMap());
        return result.getText();
    }

}
