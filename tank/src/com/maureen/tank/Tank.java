package com.maureen.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.UUID;

import com.maureen.tank.net.BulletNewMsg;
import com.maureen.tank.net.Client;
import com.maureen.tank.net.TankJoinMsg;

public class Tank {
	private static final int SPEED = 3;
	public static int WIDTH = ResourceMgr.goodTankD.getWidth();
	public static int HEIGHT = ResourceMgr.goodTankD.getHeight();
	Rectangle rect = new  Rectangle();
	
	UUID id = UUID.randomUUID();
	
	private Random random = new Random();

	private int x, y;

	private Dir dir = Dir.DOWN;
	private boolean moving = false;

	private TankFrame tf = null;

	private boolean living = true;
	private Group group = Group.BAD;
	
	public Tank(TankJoinMsg msg) {
		this.x = msg.x;
		this.y = msg.y;
		this.dir = msg.dir;
		this.moving = msg.moving;
		this.group = msg.group;
		this.id = msg.id;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
	}
	
	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = this.WIDTH;
		rect.height = this.HEIGHT;
	}

	private void boundsCheck() {
		if(this.x < 2) x = 2;
		if (this.y < 28) y =28;
		if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
		if(this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT- Tank.HEIGHT - 2;
	}

	public void die() {
		this.living = false;
		int eX = this.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
		int eY = this.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
		TankFrame.INSTANCE.explodes.add(new Explode(eX, eY));
		
	}

	public void fire() {
		int bX = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
		
		//���ӵ��������Ϣ���ݸ�������
		Bullet b = new Bullet(this.id, bX, bY, this.dir, this.group, this.tf);
		
		tf.bullets.add(b);
		
		Client.INSTANCE.send(new BulletNewMsg(b));
		
		if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
	}

	public Dir getDir() {
		return dir;
	}

	public Group getGroup() {
		return group;
	}

	public UUID getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isLiving() {
		return living;
	}

	public boolean isMoving() {
		return moving;
	}

	private void move() {
		if (!living) return;
		
		if (!moving) return;
		
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

	public void paint(Graphics g) {
		//uuid on head
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString(id.toString(), this.x, this.y - 20);
		g.drawString("live=" + living, x, y - 10);
		g.setColor(c);
		
		if(!living) {
			moving = false;
			Color cc = g.getColor();
			g.setColor(Color.WHITE);
			g.drawRect(x, y, WIDTH, HEIGHT);
			g.setColor(cc);
			return;
		}

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

	private void randomDir() {
		this.dir = Dir.values()[random.nextInt(4)];

	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setId(UUID id) {
		this.id = id;
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
