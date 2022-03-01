package gov.iti.jets.server.persistance.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author mina
 */
public enum Util {
	INSTANCE;

	public byte[] fromImageToArrayOfBytes(Image image, String format) throws IOException {
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		byte[] imageAsBytes;
		try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
			ImageIO.write(bImage, format, byteArrayOutputStream);
			imageAsBytes = byteArrayOutputStream.toByteArray();
		}
		return imageAsBytes;
	}

	public Image fromArrayOfBytesToImage(byte[] imageAsBytes) throws IOException {
		Image image;
		try (var byteArrayInputStream = new ByteArrayInputStream(imageAsBytes)) {
			image = SwingFXUtils.toFXImage(ImageIO.read(byteArrayInputStream), null);
			return image;
		}
	}

}
