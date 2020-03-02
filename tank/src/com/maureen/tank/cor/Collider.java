package com.maureen.tank.cor;

import com.maureen.tank.GameObject;

public interface Collider {
	boolean collide(GameObject o1, GameObject o2);
}
