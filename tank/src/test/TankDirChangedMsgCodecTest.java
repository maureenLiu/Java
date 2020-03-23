package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.maureen.tank.Dir;
import com.maureen.tank.net.MsgDecoder;
import com.maureen.tank.net.MsgEncoder;
import com.maureen.tank.net.MsgType;
import com.maureen.tank.net.TankDirChangedMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

class TankDirChangedMsgCodecTest {

	@Test
	void testEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgEncoder());
		
		UUID id = UUID.randomUUID();
		TankDirChangedMsg msg = new TankDirChangedMsg(id, 10, 20, Dir.UP);
		
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankDirChanged, msgType);
		
		int length = buf.readInt();
		assertEquals(28, length);
		
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		int x = buf.readInt();
		int y = buf.readInt();
		int dirOrdinal = buf.readInt();
		Dir dir = Dir.values()[dirOrdinal];
		
		assertEquals(id, uuid);
		assertEquals(10, x);
		assertEquals(20, y);
		assertEquals(Dir.UP, dir);
	}
	
	@Test
	void testDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgDecoder());
		
		UUID id = UUID.randomUUID();
		TankDirChangedMsg msg = new TankDirChangedMsg(id, 10, 20, Dir.UP);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankDirChanged.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		
		ch.writeInbound(buf.duplicate());
		
		TankDirChangedMsg msgR = (TankDirChangedMsg)ch.readInbound();
		assertEquals(MsgType.TankDirChanged, msgR.getMsgType());
		assertEquals(Dir.UP, msgR.getDir());
		assertEquals(10, msgR.getX());
		assertEquals(20, msgR.getY());
	}

}
