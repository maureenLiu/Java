package com.maureen.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceMgr {
	public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD;
	public static BufferedImage badTankL, badTankU, badTankR, badTankD;
	public static BufferedImage bulletL, bulletU, bulletR,bulletD;
	//爆炸过程有16张图
	public static BufferedImage[] explodes = new BufferedImage[16]; 
	
	static { //当ResourceMgr.class文件被load到内存的时候，静态语句块自动执行，BufferedImage就被初始化了
		try { //图片全部被load到了内存中
			goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			goodTankL = ImageUtil.rotateImage(goodTankU,-90);
			goodTankR = ImageUtil.rotateImage(goodTankU,90);
			goodTankD = ImageUtil.rotateImage(goodTankU,180);
			
			badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			badTankL = ImageUtil.rotateImage(badTankU,-90);
			badTankR = ImageUtil.rotateImage(badTankU,90);
			badTankD = ImageUtil.rotateImage(badTankU,180);
		
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
