package com.maureen.tank;

import java.awt.Graphics;

public class Explode extends GameObject {
	public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
	private int x,y;
	
	private GameModel gm = null;
	
	private int step = 0;

	public Explode(int x, int y, GameModel gm) {
		this.x = x;
		this.y = y;
		this.gm  = gm;
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();
	}

	public void paint(Graphics g) {
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		
		if(step >= ResourceMgr.explodes.length) 
			gm.remove(this);
	}
	
}
