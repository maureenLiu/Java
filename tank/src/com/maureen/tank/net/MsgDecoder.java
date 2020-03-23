package com.maureen.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

import com.maureen.tank.Dir;
import com.maureen.tank.Group;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 8) return; //Message head and length must be received. head and length both are int type, so the total bytes is 4 + 4
        
        in.markReaderIndex(); //标记开始读的位置
        
        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();
        //读完length后，读指针已经指到了第8个字节的位置处
        if(in.readableBytes() < length) {
        	in.resetReaderIndex(); //如果长度不为length，下一次还是从头执行decode方法，所以要重置读指针(在哪标记的就重置到哪)，使其重新指向之前标记的位置
        	return;
        }
        
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        
        Msg msg = null;
        //读出字节数组的内容进行解析
        //如果想增加新消息的时候如下这段代码不改变可以用 反射(reflection)
        msg = (Msg)Class.forName("com.maureen.tank.net." + msgType.toString() + "Msg").getDeclaredConstructor().newInstance();
        /*switch(msgType) {
	        case TankJoin:
	        	msg = new TankJoinMsg();
	        	break;
	        case TankStartMoving:
	        	msg = new TankStartMovingMsg();
	        	break;
	        case TankStop:
	        	msg = new TankStopMsg();
	        case TankDirChanged:
	        	msg = new TankDirChangedMsg();
        	default:
        		break;
        } */
        msg.parse(bytes); //设定对象的各个属性
    	out.add(msg);
    }
}
