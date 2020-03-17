package com.maureen.tank.strategy;

import java.io.Serializable;

import com.maureen.tank.Tank;

public interface FireStrategy extends Serializable {
	void fire(Tank t);
}
