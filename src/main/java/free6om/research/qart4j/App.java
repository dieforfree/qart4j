package free6om.research.qart4j;

import com.google.zxing.common.BitMatrix;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Hello world!
 */
public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger("test");

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/main/config/log4j.properties");

        try {
//            Image image = new Image("input.png", 4, 4, "QR Code Symbol", 1, 2, 2, 4, 0, 0, false, System.currentTimeMillis(), false, false, false);

            Plan plan = Plan.newPlan(new Version(1), Level.L, new Mask(1));
            BitMatrix bitMatrix = Plan.encode(plan, 4, 2, new Raw("QR Code Symbol"));
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get("output.png"));
        } catch (IOException e) {
            LOGGER.error("new image error", e);
        } catch (QArtException e) {
            LOGGER.error("encode error", e);
        }

    }

    private static BitMatrix transform(int[][] target) {
        BitMatrix output = new BitMatrix(target[0].length, target.length);

        int topPadding = 0, leftPadding = 0;
        int inputHeight = target.length, inputWidth = target[0].length;
        int multiple = 1;

        for (int inputY = 0, outputY = topPadding; inputY < inputHeight; inputY++, outputY += multiple) {
            // Write the contents of this row of the barcode
            for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
                if(target[inputY][inputX] < 128) {
                    output.setRegion(outputX, outputY, multiple, multiple);
                }
            }
        }

        return output;
    }
}
