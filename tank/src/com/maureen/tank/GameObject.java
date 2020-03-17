package com.maureen.tank;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class GameObject implements  Serializable {
	public int x;
	public int y;
	
	public abstract void paint(Graphics g);
	public abstract int getWidth();
	public abstract int getHeight();
}
