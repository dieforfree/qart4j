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
//            Plan plan = Plan.newPlan(new Version(7), Level.Q, new Mask(3));
//            BitMatrix bitMatrix = Plan.encode(plan, 4, 2, new Raw("QR Code Symbol 测试#"), new Number("0123456789"), new Alpha("%0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:"));

//            Plan plan = Plan.newPlan(new Version(6), Level.L, new Mask(2));
//            BitMatrix bitMatrix = Plan.encode(plan, 4, 2, new Raw("http://flkurl.com/mixin#"), new Number("341336767999687512426681334674855848685490760993186151597244699341082462453038256905008000309310469341341340000000000682694128232341256037999999170682941342682253341375999999427041330640952680347298259336021911346094965653290174699880627685898640359505341341712"));

            Image image = new Image("input.png", 4, 4, "http://flkurl.com/mixin", 6, 2, 2, 4, 0, 0, false, System.currentTimeMillis(), false, false, false);
            BitMatrix bitMatrix = image.encode();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get("output.png"));
        } catch (Exception e) {
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
