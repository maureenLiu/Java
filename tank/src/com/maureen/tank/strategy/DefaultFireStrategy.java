package com.maureen.tank.strategy;

import com.maureen.tank.Audio;
import com.maureen.tank.Bullet;
import com.maureen.tank.GameModel;
import com.maureen.tank.Group;
import com.maureen.tank.Tank;
import com.maureen.tank.decorator.RectDecorator;
import com.maureen.tank.decorator.TailDecorator;

public class DefaultFireStrategy implements FireStrategy {

	@Override
	public void fire(Tank t) {
		int bX = t.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = t.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

		//TODO:Bug? new Bullet把自己加了一遍
//		GameModel.getInstance().add(
//				new RectDecorator(
//						new TailDecorator(
//						new Bullet(bX, bY, t.dir, t.group))));
		new Bullet(bX, bY, t.dir, t.group);

		if (t.group == Group.GOOD)
			new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
	}

}
