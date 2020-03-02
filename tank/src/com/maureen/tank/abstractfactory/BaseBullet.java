package com.maureen.tank.abstractfactory;

import java.awt.Graphics;

public abstract class BaseBullet {
	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);
}
