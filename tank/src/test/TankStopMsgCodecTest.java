package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.maureen.tank.net.MsgDecoder;
import com.maureen.tank.net.MsgEncoder;
import com.maureen.tank.net.MsgType;
import com.maureen.tank.net.TankStopMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

class TankStopMsgCodecTest {

	@Test
	void testEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgEncoder());
		
		UUID id = UUID.randomUUID();
		TankStopMsg msg = new TankStopMsg(id, 10, 20);
		
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankStop, msgType);
		
		int length = buf.readInt();
		assertEquals(24, length);
		
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		int x = buf.readInt();
		int y = buf.readInt();
		
		assertEquals(id, uuid);
		assertEquals(10, x);
		assertEquals(20, y);
	}
	
	@Test
	void testDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgDecoder());
		
		UUID id = UUID.randomUUID();
		TankStopMsg msg = new TankStopMsg(id, 10, 20);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankStop.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		
		ch.writeInbound(buf.duplicate());
		
		TankStopMsg msgR = (TankStopMsg)ch.readInbound();
		assertEquals(id, msgR.getId());
		assertEquals(10, msgR.getX());
		assertEquals(20, msgR.getY());
		assertEquals(MsgType.TankStop, msgR.getMsgType());
	}
}
