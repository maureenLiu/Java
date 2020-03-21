package com.maureen.tank.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

import com.maureen.tank.Dir;
import com.maureen.tank.Group;
import com.maureen.tank.Tank;

public class TankJoinMsg { //用于网络传输的消息
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.dir = t.getDir();
		this.moving = t.isMoving();
		this.group = t.getGroup();
		this.id = t.getId();
	} 
    
    public TankJoinMsg() {
    	
    }
    

	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.group = group;
		this.id = id;
	}



	@Override
    public String toString() {
        return "TankMsg{" + "x=" + x + ", y=" + y + '}';
    }
	
	
	public byte[] toBytes() {//将整个消息转换成字节数组
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal()); //dir.ordinal()-数组下标值
			dos.writeBoolean(moving); //boolean类型的值，往外写是一个字节
			dos.writeInt(group.ordinal());
			dos.writeLong(id.getMostSignificantBits()); //UUID的高64位，当做一个Long数据类型写出去
			dos.writeLong(id.getLeastSignificantBits());//UUID的低64位
			//dos.writeUTF(name);//writeUTF写字符串
			dos.flush();
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(baos != null) baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(dos != null) dos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return bytes;
	}
}
