package com.maureen.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.maureen.tank.cor.ColliderChain;

public class GameModel {
	private static final GameModel INSTANCE = new GameModel();
	
	static {
		INSTANCE.init();
	}
	
	Tank myTank = null; 

//	List<Bullet> bullets = new ArrayList<>();
//	List<Tank> enemines = new ArrayList<>();
//	List<Explode> explodes = new ArrayList<>();
	
	ColliderChain chain = new ColliderChain();
	
	private  List<GameObject> objects = new ArrayList<>();
	
	public static GameModel getInstance() {
		return INSTANCE;
	}

	private GameModel() { }
	
	private void init() {
		// 初始化主战坦克
		myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);

		int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

		// 初始化敌方坦克
		for (int i = 0; i < initTankCount; i++) {
			new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD);
		}

		// 初始化墙
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 300, 50, 200));
	}
	
	public void add(GameObject go) {
		this.objects.add(go);
	}
	
	public void remove(GameObject go) {
		this.objects.remove(go);
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
//		g.drawString("子弹的数量:" + bullets.size(), 10, 60);
//		g.drawString("敌人的数量:" + enemines.size(), 10, 80);
//		g.drawString("爆炸的数量:" + explodes.size(), 10, 100);
		g.setColor(c);

		myTank.paint(g);
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).paint(g);
		}
		
		//互相碰撞
		for(int i = 0; i < objects.size() - 1; i++) {
			for(int j = i+1; j < objects.size(); j++) {
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
				//for
				chain.collide(o1,o2);
			}
		}		

//		for (int i = 0; i < bullets.size(); i++) {
//			for (int j = 0; j < enemines.size(); j++)
//				bullets.get(i).collideWith(enemines.get(j));
//		}


	}

	public Tank getMainTank() {
		return myTank;
	}
	
	//1、第一种方式：将myTank和objects序列化，先写的就要先读，save()和load()就是这种方法的code
	//2、第二种方式：直接将GameModel实现Serializable接口，TankFrame里直接将gm写到硬盘
	public void save() { //Tank状态写到硬盘中
		File f = new File("E:/tank.data");
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			//为了写文件成功，myTank和objects都要实现Serializable接口，所以其父类GameObject实现Serializable接口
			oos.writeObject(myTank); 
			oos.writeObject(objects);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public void load() { //从硬盘中load保存的tank的位置、朝向、子弹位置等
		File f = new File("E:/tank.data");
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			//先写哪个就先读哪个
			myTank = (Tank)ois.readObject(); 
			objects = (List)ois.readObject();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
