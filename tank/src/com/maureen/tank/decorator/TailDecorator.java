package com.maureen.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.maureen.tank.GameObject;

public class TailDecorator extends GODecorator {

	public TailDecorator(GameObject go) {
		super(go);
	}
	
	@Override
	public void paint(Graphics g) {
		this.x = go.x;
		this.y = go.y;
		
		go.paint(g);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawLine(super.go.x, super.go.y, super.go.x + super.go.getWidth(), super.go.y + super.go.getHeight());
		g.setColor(c);
	}

	@Override
	public int getWidth() {
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		return super.go.getHeight();
	}

}
