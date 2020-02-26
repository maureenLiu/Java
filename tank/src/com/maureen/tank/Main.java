package com.maureen.tank;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tf = new TankFrame();
		
		//初始化敌方坦克
		for(int i = 0; i < 5; i++) {
			tf.enemies.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD,tf));
		}
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
	}

}
