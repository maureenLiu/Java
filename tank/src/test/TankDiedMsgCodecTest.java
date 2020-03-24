package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.maureen.tank.net.MsgDecoder;
import com.maureen.tank.net.MsgEncoder;
import com.maureen.tank.net.MsgType;
import com.maureen.tank.net.TankDiedMsg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

class TankDiedMsgCodecTest {

	@Test
	void testEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgEncoder());
		
		UUID playerId = UUID.randomUUID();
		UUID id = UUID.randomUUID();
		TankDiedMsg msg = new TankDiedMsg(playerId,id);
		
		ch.writeOutbound(msg);
		
		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankDied, msgType);
		
		int length = buf.readInt();
		assertEquals(32, length);
		
		UUID bulletId = new UUID(buf.readLong(), buf.readLong());
		UUID tankId = new UUID(buf.readLong(), buf.readLong());
		assertEquals(playerId, bulletId);
		assertEquals(id, tankId);
	}

	@Test
	void testDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgDecoder());
		
		UUID playerId = UUID.randomUUID();
		UUID id = UUID.randomUUID();
		TankDiedMsg msg = new TankDiedMsg(playerId,id);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankDied.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		
		ch.writeInbound(buf.duplicate());
		
		TankDiedMsg msgR = (TankDiedMsg)ch.readInbound();
		assertEquals(MsgType.TankDied, msgR.getMsgType());
		assertEquals(playerId, msgR.getBulletId());
		assertEquals(id, msgR.getTankId());
	}
}
