package com.maureen.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {
	
	public static final TankFrame INSTANCE = new TankFrame();
	
	Random r = new Random();
	//Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD, this);
	Tank myTank = new Tank(r.nextInt(GAME_WIDTH), r.nextInt(GAME_HEIGHT), Dir.DOWN, Group.GOOD, this);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> enemies = new ArrayList<>();
	List<Explode> explodes = new ArrayList<>();
	
	static final int GAME_WIDTH = 1080;
	static final int GAME_HEIGHT = 960;
	
	public TankFrame( ) {
		setSize(GAME_WIDTH,GAME_HEIGHT);
		setResizable(false);
		setTitle("Tank war");
		setVisible(true);
		
		this.addKeyListener(new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	Image offScreenImage = null; //瀹氫箟鍦ㄥ唴瀛樹腑鐨勫浘鐗�
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("�ӵ�����"+bullets.size(), 10, 60);
		g.drawString("����̹������"+enemies.size(), 10, 80);
		g.drawString("��ը��:"+explodes.size(), 10, 100);
		g.setColor(c);
		
		myTank.paint(g);
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint(g);
		} 
		
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint(g);
		}
		
		for(int i = 0; i < explodes.size(); i++) {
			explodes.get(i).paint(g);
		}
		
		//Collision detect
		for(int i =0 ; i < bullets.size(); i++) {
			for(int j = 0; j < enemies.size(); j++) 
				bullets.get(i).collodeWith(enemies.get(j)); 
		}
		
//		for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
//			Bullet b = it.next();
//			if(!b.isLive()) it.remove();
//		}
	}
	
	class MyKeyListener extends KeyAdapter {
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT: //VK:virtual key
					bL = true;
					break;
				case KeyEvent.VK_UP:
					bU = true;
					break; 
				case KeyEvent.VK_RIGHT:
					bR = true;
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					break;
				default:
					break;
			}
			setMainTankDir();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
				case KeyEvent.VK_LEFT: //VK:virtual key
					bL = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break; 
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_CONTROL:
					myTank.fire();
					break;
				default:
					break;
			}
				
			setMainTankDir();
		}

		private void setMainTankDir() {
			if(!bL && !bU && !bR && !bD) myTank.setMoving(false);
			else {
				myTank.setMoving(true);
				if(bL) myTank.setDir(Dir.LEFT);
				if(bU) myTank.setDir(Dir.UP);
				if(bR) myTank.setDir(Dir.RIGHT);
				if(bD) myTank.setDir(Dir.DOWN);
			}
		}
		
	}
	
	public Tank getMainTank() {
		return this.myTank;
	}
}
