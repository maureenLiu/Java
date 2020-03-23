package com.maureen.tank;

import com.maureen.tank.net.Client;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true); //Show the TankFrame window
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start(); //Play music
		new Thread(()->{
			while(true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tf.repaint();
			}
		}).start();
		
		//Connect to the server
		//or you can new a thread to run this
		Client.INSTANCE.connect();
		
	}

}
