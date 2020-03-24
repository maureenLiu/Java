package com.maureen.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.maureen.tank.Dir;
import com.maureen.tank.Group;
import com.maureen.tank.Tank;
import com.maureen.tank.TankFrame;

public class TankJoinMsg extends Msg { //用于网络传输的消息
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
	public void parse(byte[] bytes) { //和toBytes()是两个方向的操作
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		
		try {
			//TODO: 先读TYPE信息，根据TYPE信息处理不同的小
			//略过消息类型
			//dis.readInt();
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.moving = dis.readBoolean();
			this.group = group.values()[dis.readInt()];
			this.id = new UUID(dis.readLong(), dis.readLong());
			//this.name = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName())
				.append("[")
				.append("uuid=" + id + " | ")
				// .append("name=" + name + " | ")
				.append("x=" + x + " | ")
				.append("y=" + y + " | ")
				.append("moving=" + moving + " | ")
				.append("dir=" + dir + " | ")
				.append("group=" + group + " | ")
				.append("]");
		return builder.toString();
			
	}

	@Override
	public void handle() {
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())
				|| TankFrame.INSTANCE.findTankByUUID(this.id) != null)
			return;
		System.out.println(this);
		Tank t = new Tank(this);
		TankFrame.INSTANCE.addTank(t);
		
		//send a new TankJoinMsg to the new joined tank
		Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}
}
