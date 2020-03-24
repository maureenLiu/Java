package com.maureen.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.maureen.tank.Bullet;
import com.maureen.tank.Dir;
import com.maureen.tank.Group;
import com.maureen.tank.TankFrame;

public class BulletNewMsg extends Msg {
	UUID playerId;
	UUID id;
	int x;
	int y;
	Dir dir;
	Group group;
	
	public BulletNewMsg() {
		
	}
	
	public BulletNewMsg(Bullet bullet) {
		this.playerId = bullet.getPlayerId();
		this.id = bullet.getId();
		this.x = bullet.getX();
		this.y = bullet.getY();
		this.dir = bullet.getDir();
		this.group = bullet.getGroup();
	}
	
	public BulletNewMsg(UUID playerId, UUID id, int x, int y, Dir dir, Group group) {
		this.playerId = playerId;
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
	}

	
	public Dir getDir() {
		return dir;
	}

	public Group getGroup() {
		return group;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.BulletNew;
	}

	public UUID getPlayerId() {
		return playerId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void handle() {
		if(this.playerId.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;
		
		Bullet bullet = new Bullet(this.playerId, x, y, dir, group, TankFrame.INSTANCE);
		bullet.setId(this.id);
		TankFrame.INSTANCE.addBullet(bullet);
	}

	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			this.playerId = new UUID(dis.readLong(), dis.readLong());
			this.id = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
			this.group = Group.values()[dis.readInt()];
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeLong(playerId.getMostSignificantBits());
			dos.writeLong(playerId.getLeastSignificantBits());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(group.ordinal());
			dos.flush();
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(baos != null)
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(dos != null)
				try {
					dos.close();
				} catch (IOException e) {
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
			   .append("playerid=" + playerId + " | ")
			   .append("uuid=" + id + " | ")
			   .append("x=" + x + " | ")
			   .append("y=" + y + " | ")
			   .append("dir=" + dir + " | ")
			   .append("group=" + group + " | ")
			   .append("]");
		return builder.toString();
	}

}
