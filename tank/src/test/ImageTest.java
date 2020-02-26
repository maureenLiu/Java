package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageTest {

	@Test
	void test() {
		try {
			BufferedImage image = ImageIO.read(new File("D:/Maureen/github/Java/tank/image/tank.jpg"));
			assertNotNull(image);
			
			BufferedImage image2 = ImageIO.read(ImageTest/*this*/.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
			assertNotNull(image2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
