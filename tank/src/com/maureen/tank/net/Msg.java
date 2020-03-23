package com.maureen.tank.net;

public abstract class Msg {
	public abstract void handle(); //how to handle if receive message
	public abstract byte[] toBytes(); //convert to byte array.
	public abstract void parse(byte[] bytes);
	public abstract MsgType getMsgType();
}
