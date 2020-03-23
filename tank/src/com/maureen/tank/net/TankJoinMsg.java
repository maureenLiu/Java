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

public class TankJoinMsg extends Msg { //�������紫�����Ϣ
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

	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		
		try {
			//TODO: �ȶ�TYPE��Ϣ������TYPE��Ϣ����ͬ��С
			//�Թ���Ϣ����
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
	public byte[] toBytes() {//��������Ϣת�����ֽ�����
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal()); //dir.ordinal()-�����±�ֵ
			dos.writeBoolean(moving); //boolean���͵�ֵ������д��һ���ֽ�
			dos.writeInt(group.ordinal());
			dos.writeLong(id.getMostSignificantBits()); //UUID�ĸ�64λ������һ��Long��������д��ȥ
			dos.writeLong(id.getLeastSignificantBits());//UUID�ĵ�64λ
			//dos.writeUTF(name);//writeUTFд�ַ���
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
				|| TankFrame.INSTANCE.findByUUID(this.id) != null)
			return;
		System.out.println(this);
		Tank t = new Tank(this);
		TankFrame.INSTANCE.addTank(t);
		
		//send a new TankJoinMsg to the new joined tank
		Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
		
	}
}
