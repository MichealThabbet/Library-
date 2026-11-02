package org.apache.poi.ss.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
public class ImageUtils {
    public static final int PIXEL_DPI = 96;
    private static final POILogger logger = POILogFactory.getLogger(ImageUtils.class);

    public static Dimension getImageDimension(InputStream inputStream, int i) {
        Dimension dimension = new Dimension();
        if (i == 5 || i == 6 || i == 7) {
            try {
                ImageInputStream imageInputStreamCreateImageInputStream = ImageIO.createImageInputStream(inputStream);
                ImageReader imageReader = (ImageReader) ImageIO.getImageReaders(imageInputStreamCreateImageInputStream).next();
                imageReader.setInput(imageInputStreamCreateImageInputStream);
                BufferedImage bufferedImage = imageReader.read(0);
                int[] resolution = getResolution(imageReader);
                if (resolution[0] == 0) {
                    resolution[0] = 96;
                }
                if (resolution[1] == 0) {
                    resolution[1] = 96;
                }
                dimension.width = (bufferedImage.getWidth() * 96) / resolution[0];
                dimension.height = (bufferedImage.getHeight() * 96) / resolution[1];
                imageReader.dispose();
                imageInputStreamCreateImageInputStream.close();
            } catch (IOException e) {
                logger.log(POILogger.WARN, (Throwable) e);
            }
        } else {
            logger.log(POILogger.WARN, "Only JPEG, PNG and DIB pictures can be automatically sized");
        }
        return dimension;
    }

    public static int[] getResolution(ImageReader imageReader) throws IOException {
        Element element = (Element) imageReader.getImageMetadata(0).getAsTree("javax_imageio_1.0");
        NodeList elementsByTagName = element.getElementsByTagName("HorizontalPixelSize");
        int i = 96;
        int i2 = (elementsByTagName == null || elementsByTagName.getLength() != 1) ? 96 : (int) (25.4d / Float.parseFloat(((Element) elementsByTagName.item(0)).getAttribute("value")));
        NodeList elementsByTagName2 = element.getElementsByTagName("VerticalPixelSize");
        if (elementsByTagName2 != null && elementsByTagName2.getLength() == 1) {
            i = (int) (25.4d / Float.parseFloat(((Element) elementsByTagName2.item(0)).getAttribute("value")));
        }
        return new int[]{i2, i};
    }
}
