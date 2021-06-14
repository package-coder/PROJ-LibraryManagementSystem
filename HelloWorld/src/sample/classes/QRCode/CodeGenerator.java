package sample.classes.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class CodeGenerator {
    private static BitMatrix encodeMatrix(String data,
                                   Charset charset,
                                   int width,
                                   int height,
                                   BarcodeFormat codeFormat) throws WriterException {

        var map = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        return new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                codeFormat, width , height, map);

    }

    public static BufferedImage generateQRImage(String data, Charset charset, int width, int height) throws WriterException {
        var matrix = encodeMatrix(data, charset, width, height, BarcodeFormat.QR_CODE);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    public static BufferedImage generateQRImage(String data, int width, int height) throws WriterException {
        return generateQRImage(data, Charset.defaultCharset(), width, height);
    }

    public static void generateQRImageToFile(String data, String path, Charset charset, int width, int height) throws WriterException, IOException {
        var matrix = encodeMatrix(data, charset, width, height, BarcodeFormat.QR_CODE);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static void generateQRImageToFile(String data, String path, int width, int height) throws WriterException, IOException {
        generateQRImageToFile(data, path, Charset.defaultCharset(), width, height);
    }

    public static BufferedImage generateCodabarImage(String data, Charset charset, int width, int height) throws WriterException {
        var matrix = encodeMatrix(data, charset, width, height, BarcodeFormat.CODABAR);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    public static BufferedImage generateCodabarImage(String data, int width, int height) throws WriterException {
        return generateCodabarImage(data, Charset.defaultCharset(), width, height);
    }

    public static void generateCodabarImageToFile(String data, String path, Charset charset, int width, int height) throws WriterException, IOException {
        var matrix = encodeMatrix(data, charset, width, height, BarcodeFormat.CODABAR);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
    public static void generateCodabarImageToFile(String data, String path, int width, int height) throws WriterException, IOException {
        generateCodabarImageToFile(data, path, Charset.defaultCharset(), width, height);
    }
}
