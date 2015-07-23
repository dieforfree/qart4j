package free6om.research.qart4j;

import com.google.zxing.common.BitMatrix;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
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
import java.util.Arrays;
import java.util.Random;

/**
 * Hello world!
 */
public class QArt {
    private static final Logger LOGGER = LoggerFactory.getLogger("test");

    public static void main(String[] args) {
        OptionParser parser = new OptionParser() {
            {
                acceptsAll(Arrays.asList("help", "?", "h"), "show this help.");
                acceptsAll(Arrays.asList("l", "log4j")).withRequiredArg()
                        .ofType(String.class)
                        .describedAs("log config file path.")
                        .defaultsTo("./src/main/config/log4j.properties");
                acceptsAll(Arrays.asList("i", "input")).withRequiredArg()
                        .ofType(String.class)
                        .describedAs("input image file")
                        .defaultsTo("input.png");
                acceptsAll(Arrays.asList("dx")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("start pixel of input image in x direction")
                        .defaultsTo(0);
                acceptsAll(Arrays.asList("dy")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("start pixel of input image in y direction")
                        .defaultsTo(0);
                acceptsAll(Arrays.asList("u", "url")).withRequiredArg()
                        .ofType(String.class)
                        .describedAs("URL to encode")
                        .defaultsTo("http://free6om.me");
                acceptsAll(Arrays.asList("v", "version")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("QR version: 1 - 40")
                        .defaultsTo(6);
                acceptsAll(Arrays.asList("m", "mask")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("QR mask: 0 - 7")
                        .defaultsTo(2);
                acceptsAll(Arrays.asList("q", "quiet")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("QR quiet zone")
                        .defaultsTo(2);
                acceptsAll(Arrays.asList("s", "scale")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("output file size/QR code size")
                        .defaultsTo(4);
                acceptsAll(Arrays.asList("r", "rotation")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("rotation of the image in clockwise: 0 - 3")
                        .defaultsTo(0);
                acceptsAll(Arrays.asList("z", "size")).withRequiredArg()
                        .ofType(Integer.class)
                        .describedAs("not used yet")
                        .defaultsTo(0);
                acceptsAll(Arrays.asList("rand")).withRequiredArg()
                        .ofType(Boolean.class)
                        .describedAs("random select bit to set 0 when needed, not implemented yet")
                        .defaultsTo(Boolean.FALSE);
                acceptsAll(Arrays.asList("seed")).withRequiredArg()
                        .ofType(Long.class)
                        .describedAs("random seed, -1 use System.currentMillis()")
                        .defaultsTo(-1L);
                acceptsAll(Arrays.asList("d", "dither")).withRequiredArg()
                        .ofType(Boolean.class)
                        .describedAs("dither image or not")
                        .defaultsTo(Boolean.FALSE);
                acceptsAll(Arrays.asList("onlyData")).withRequiredArg()
                        .ofType(Boolean.class)
                        .describedAs("only use data bits to emulate input image")
                        .defaultsTo(Boolean.FALSE);
                acceptsAll(Arrays.asList("c", "saveControl")).withRequiredArg()
                        .ofType(Boolean.class)
                        .describedAs("show pixel we have control")
                        .defaultsTo(Boolean.FALSE);
                acceptsAll(Arrays.asList("f", "format")).withRequiredArg()
                        .ofType(String.class)
                        .describedAs("output image format")
                        .defaultsTo("PNG");
                acceptsAll(Arrays.asList("o", "output")).withRequiredArg()
                        .ofType(String.class)
                        .describedAs("output image file")
                        .defaultsTo("output.png");

            }
        };
        OptionSet options = parser.parse(args);
        if (options.hasArgument("help") || options.has("?")
                || options.has("h")) {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException e) {
            }
            return;
        }

        String log4j = (String) options.valueOf("l");
        String input = (String) options.valueOf("i");
        int dx = (Integer) options.valueOf("dx");
        int dy = (Integer) options.valueOf("dy");
        String url = (String) options.valueOf("u");
        int version = (Integer) options.valueOf("v");
        int mask = (Integer) options.valueOf("m");
        int quietZone = (Integer) options.valueOf("q");
        int scale = (Integer) options.valueOf("s");
        int rotation = (Integer) options.valueOf("r");
        int size = (Integer) options.valueOf("z");
        boolean randControl = (Boolean) options.valueOf("rand");
        long seed = (Long) options.valueOf("seed");
        if (seed == -1) {
            seed = System.currentTimeMillis();
        }
        boolean dither = (Boolean) options.valueOf("d");
        boolean onlyDataBits = (Boolean) options.valueOf("onlyData");
        boolean saveControl = (Boolean) options.valueOf("saveControl");

        String outputFormat = (String) options.valueOf("f");
        String output = (String) options.valueOf("o");

        PropertyConfigurator.configure(log4j);

        try {
            Image image = new Image(input, dx, dy, url,
                    version, mask, quietZone, scale,
                    rotation, size, randControl, seed,
                    dither, onlyDataBits, saveControl);

            BitMatrix bitMatrix = image.encode();
            MatrixToImageWriter.writeToPath(bitMatrix, outputFormat, Paths.get(output));
        } catch (Exception e) {
            LOGGER.error("encode error", e);
        }

    }
}
