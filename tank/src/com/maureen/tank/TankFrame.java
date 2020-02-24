package com.maureen.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
	
	int x = 200, y = 200;
	public TankFrame( ) {
		setSize(800,600);
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

	@Override
	public void paint(Graphics g) {
		g.fillRect(x,y, 50, 50);
		x += 10;
		y += 10;
	}
	
	class MyKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("keyPressed");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("keyReleased");
		}
		
	}
}
