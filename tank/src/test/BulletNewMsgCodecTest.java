package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.maureen.tank.Dir;
import com.maureen.tank.Group;
import com.maureen.tank.net.BulletNewMsg;
import com.maureen.tank.net.MsgDecoder;
import com.maureen.tank.net.MsgEncoder;
import com.maureen.tank.net.MsgType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

class BulletNewMsgCodecTest {

	@Test
	void testEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgEncoder());
		
		UUID playerId = UUID.randomUUID();
		UUID bulletId = UUID.randomUUID();
		
		BulletNewMsg msg = new BulletNewMsg(playerId, bulletId, 10, 20, Dir.LEFT, Group.GOOD);
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.BulletNew, msgType);
		
		int length = buf.readInt();
		assertEquals(48, length);
		
		UUID playerIdR = new UUID(buf.readLong(), buf.readLong());
		assertEquals(playerId, playerIdR);
		
		UUID bulletIdR = new UUID(buf.readLong(), buf.readLong());
		assertEquals(bulletId, bulletIdR);
		
		int x = buf.readInt();
		int y = buf.readInt();
		Dir dir = Dir.values()[buf.readInt()];
		Group group = Group.values()[buf.readInt()];
		
		assertEquals(10, x);
		assertEquals(20, y);
		assertEquals(Dir.LEFT, dir);
		assertEquals(Group.GOOD, group);
	}

	@Test
	void testDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgDecoder());
		
		UUID playerId = UUID.randomUUID();
		UUID bulletId = UUID.randomUUID();
		BulletNewMsg msg = new BulletNewMsg(playerId, bulletId, 10, 20, Dir.LEFT, Group.GOOD);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.BulletNew.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		
		ch.writeInbound(buf.duplicate());
		
		BulletNewMsg msgR = (BulletNewMsg)ch.readInbound();
		assertEquals(MsgType.BulletNew, msgR.getMsgType());
		assertEquals(10, msgR.getX());
		assertEquals(20, msgR.getY());
		assertEquals(Dir.LEFT, msgR.getDir());
		assertEquals(Group.GOOD, msgR.getGroup());
	}
}
