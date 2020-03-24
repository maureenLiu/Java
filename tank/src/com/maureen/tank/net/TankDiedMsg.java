package com.maureen.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.maureen.tank.Bullet;
import com.maureen.tank.Tank;
import com.maureen.tank.TankFrame;

public class TankDiedMsg extends Msg {
	UUID bulletId; //who killed me
	UUID id;

	public TankDiedMsg(UUID playerId, UUID id) {
		this.bulletId = playerId;
		this.id = id;
	}
	
	public TankDiedMsg() {
		
	}
	
	@Override
	public void handle() {
		System.out.println("we got a tank die:" + id);
		System.out.println("and my tank is:" + TankFrame.INSTANCE.getMainTank().getId());
		Tank tt = TankFrame.INSTANCE.findTankByUUID(id);
		System.out.println("i found a tank with this id:" + tt);
		
		Bullet b = TankFrame.INSTANCE.findBulletByUUID(bulletId);
		if(b != null) {
			b.die();
		}
		
		//TODO:Remove the died tank from Map.
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			TankFrame.INSTANCE.getMainTank().die();
		else {
			Tank t = TankFrame.INSTANCE.findTankByUUID(id);
			if(t != null) 
				t.die();
		}
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeLong(bulletId.getMostSignificantBits());
			dos.writeLong(bulletId.getLeastSignificantBits());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.flush();
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (dos != null)
					dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			this.bulletId = new UUID(dis.readLong(), dis.readLong());
			this.id = new UUID(dis.readLong(), dis.readLong());
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

	@Override
	public MsgType getMsgType() {
		return MsgType.TankDied;
	}

	public UUID getBulletId() {
		return bulletId;
	}
	
	public UUID getTankId() {
		return id;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName())
		   .append("[")
		   .append("uuid=" + id + " | ")
		   .append("bulletId=" + bulletId + "|")
		   .append("id=" + id + " | ")
		   .append("]");
		return builder.toString();
	}
}
