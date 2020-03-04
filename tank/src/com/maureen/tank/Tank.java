package com.maureen.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.maureen.tank.observer.TankFireEvent;
import com.maureen.tank.observer.TankFireHanlder;
import com.maureen.tank.observer.TankFireObserver;
import com.maureen.tank.strategy.DefaultFireStrategy;
import com.maureen.tank.strategy.FireStrategy;

public class Tank extends GameObject{
	private static final int SPEED = 3;
	public static int WIDTH = ResourceMgr.goodTankD.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankD.getHeight();
	Rectangle rect = new  Rectangle();
	
	private Random random = new Random();

	public Dir dir = Dir.DOWN;

	private boolean moving = true;
	
	private boolean living = true;

	public Group group = Group.BAD;
	
	FireStrategy fs;

	public Tank(int x, int y, Dir dir, Group group) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = this.WIDTH;
		rect.height = this.HEIGHT;
		
		if(group == Group.GOOD) {
			String goodFSName = (String)PropertyMgr.get("goodFs");
			try {
				fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			fs = new DefaultFireStrategy();
		}
		
		GameModel.getInstance().add(this);
	}

	public void die() {
		this.living = false;

	}

	public void fire() {
		fs.fire(this);
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
	
	private int preX;
	private int preY;
	
	public void back() {
		x = preX;
		y = preY;
	}

	private void move() {
		//记录移动之前的位置
		preX = x;
		preY = y;
		
		if (!moving) {
			return;
		}
			
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
		
		if (this.group == Group.BAD && random.nextInt(100) > 95)
			this.fire();
		if (this.group == Group.BAD && random.nextInt(100) > 95)
			randomDir();
		
		boundsCheck();
		//update rect
		rect.x = this.x;
		rect.y = this.y;

	}

	private void boundsCheck() {
//		增加两个像素的空隙，使得更加美观
		if(this.x < 2) x = 2;
		if (this.y < 28) y =28;
		if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT- Tank.HEIGHT - 2;
		
	}

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];

	}

	public void paint(Graphics g) {
		if (!living)
			GameModel.getInstance().remove(this);

		switch (dir) {
		case LEFT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankL:ResourceMgr.badTankL, x, y, null);
			break;
		case UP:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankU:ResourceMgr.badTankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankR:ResourceMgr.badTankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankD:ResourceMgr.badTankD, x, y, null);
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

	public Rectangle getRect() {
		return rect;
	}

	public void stop() {
		moving = false;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	private List<TankFireObserver> fireObservers = Arrays.asList(new TankFireHanlder());
	public void handleFireKey() {
		TankFireEvent event = new TankFireEvent(this);
		for(TankFireObserver o: fireObservers) {
			o.actionOnFire(event);
		}
	}

}
