package com.maureen.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tank {
	private static final int SPEED = 3;
	public static int WIDTH = ResourceMgr.tankD.getWidth();

	public static int HEIGHT = ResourceMgr.tankD.getHeight();

	private Random random = new Random();
	private int x, y;

	private Dir dir = Dir.DOWN;

	private boolean moving = true;
	private TankFrame tf = null;
	private boolean living = true;

	private Group group = Group.BAD;

	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
	}

	public void die() {
		this.living = false;

	}

	public void fire() {
		int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
		tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));

	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Dir getDir() {
		return dir;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isMoving() {
		return moving;
	}

	private void move() {
		if (!moving)
			return;
		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}

		if (random.nextInt(10) > 8)
			this.fire();

	}

	public void paint(Graphics g) {
		if (!living)
			tf.enemies.remove(this);

		switch (dir) {
		case LEFT:
			g.drawImage(ResourceMgr.tankL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.tankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.tankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.tankD, x, y, null);
			break;
		}

		move();
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
