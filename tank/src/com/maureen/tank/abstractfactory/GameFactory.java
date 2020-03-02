package com.maureen.tank.abstractfactory;

import com.maureen.tank.Bullet;
import com.maureen.tank.Dir;
import com.maureen.tank.Explode;
import com.maureen.tank.Group;
import com.maureen.tank.Tank;
import com.maureen.tank.TankFrame;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);
	
	public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
	
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
}
