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
            Image image = new Image("mixin-icon.png", 4, 4, "http://flkurl.com/mixin",
                    6/*version*/, 2/*mask*/, 2/*quietZone*/, 4/*scale*/,
                    0/*rotation*/, 0/*size*/, false/*randControl*/, System.currentTimeMillis()/*seed*/,
                    false/*dither*/, false/*onlyDataBits*/, false/*saveControl*/);
            BitMatrix bitMatrix = image.encode();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get("output.png"));
        } catch (Exception e) {
            LOGGER.error("encode error", e);
        }

    }
}
