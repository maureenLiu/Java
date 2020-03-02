package com.maureen.tank.cor;

import com.maureen.tank.Bullet;
import com.maureen.tank.GameObject;
import com.maureen.tank.Tank;

public class BulletTankCollider implements Collider {

	@Override
	public boolean collide(GameObject o1, GameObject o2) {
		if(o1 instanceof Bullet && o2 instanceof Tank) {
			Bullet b = (Bullet)o1;
			Tank t = (Tank)o2;
			//TODO copy code from method collideWith
			if(b.collideWith(t)) {
				return false;
			}
			
		} else if (o1 instanceof Tank && o2 instanceof Bullet) {
			return collide(o2, o1);
		} 
		
		return true;
	}

}
