package com.maureen.tank.observer;

import com.maureen.tank.Tank;

public class TankFireHanlder implements TankFireObserver {

	@Override
	public void actionOnFire(TankFireEvent e) {
		Tank t = e.getSource();
		t.fire();
		
	}
	
}
