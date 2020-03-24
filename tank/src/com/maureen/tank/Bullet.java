package com.maureen.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

public class Bullet {
	private static final int SPEED = 10;
	
	public static final int WIDTH = ResourceMgr.bulletD.getWidth();
	public static final int HEIGHT = ResourceMgr.bulletD.getHeight();
	Rectangle rect = new  Rectangle();
	
	private UUID id = UUID.randomUUID();
	private int x,y;
	private Dir dir;
	private boolean living = true;
	private TankFrame tf = null;
	private Group group = Group.BAD;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	public void setLiving(boolean living) {
		this.living = living;
	}
	
	public boolean isLiving() {
		return living;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf  = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = this.WIDTH;
		rect.height = this.HEIGHT;
	}
	
	public void collodeWith(Tank tank) {
		if(this.group == tank.getGroup()) return;
		
		if(rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
			int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
			tf.explodes.add(new Explode(eX, eY, tf));
		}
	}

	private void die() {
		
		this.living = false;
	}

	private void move() {
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
		
		//update rect
		rect.x = this.x;
		rect.y = this.y;
		
		if(x < 0 || y < 0 ||x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) 
			living = false;
		
	}

	public void paint(Graphics g) {
		if(!living) {
			tf.bullets.remove(this);
		}
		switch(dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bulletL,x,y,null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU,x,y,null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR,x,y,null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD,x,y,null);
			break;
		}
		move();
	}
	
}
