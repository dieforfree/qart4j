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

//        Image(String filename, int dx, int dy, String URL, int version, int mask, int scale, int rotation, int size, boolean randControl, long seed, boolean dither, boolean onlyDataBits, boolean saveControl)
        try {
            Image image = new Image("logo.png", 4, 4, "http://flkurl.com/mixin", 6, 2, 2, 4, 0, 0, false, System.currentTimeMillis(), false, false, false);
            BitMatrix bitMatrix = image.encode();
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get("output.png"));
        } catch (IOException e) {
            LOGGER.error("new image error", e);
        } catch (ImageReadException e) {
            LOGGER.error("new image error", e);
        } catch (QArtException e) {
            LOGGER.error("encode error", e);
        }

    }
}
