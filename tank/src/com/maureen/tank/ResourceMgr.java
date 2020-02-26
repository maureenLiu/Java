package com.maureen.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceMgr {
	public static BufferedImage tankL, tankU, tankR,tankD;
	public static BufferedImage bulletL, bulletU, bulletR,bulletD;
	public static BufferedImage[] explodes = new BufferedImage[16];
	
	static { //当ResourceMgr.class文件被load到内存的时候，静态语句块自动执行，BufferedImage就被初始化了
		try { //图片全部被load到了内存中
			tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			tankL = ImageUtil.rotateImage(tankU,-90);
			tankR = ImageUtil.rotateImage(tankU,90);
			tankD = ImageUtil.rotateImage(tankU,180);
		
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU,-90);
			bulletR = ImageUtil.rotateImage(bulletU,90);
			bulletD = ImageUtil.rotateImage(bulletU,180);
			
			for(int i = 0; i < 16; i++) 
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i+1)+".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
