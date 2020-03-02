package com.maureen.tank.abstractfactory;

import com.maureen.tank.Bullet;
import com.maureen.tank.Dir;
import com.maureen.tank.Explode;
import com.maureen.tank.Group;
import com.maureen.tank.Tank;
import com.maureen.tank.TankFrame;

public class DefaultFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Tank(x, y, dir, group, tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new Explode(x, y, tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Bullet(x, y, dir, group, tf);
	}

}
