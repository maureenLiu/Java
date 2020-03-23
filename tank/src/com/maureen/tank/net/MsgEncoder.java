package com.maureen.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Msg> { //MessageToByteEncoder<TankMsg>:灏員ankMsg娑堟伅杞崲鎴愬瓧鑺�
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
    	buf.writeInt(msg.getMsgType().ordinal()); //write Message Type
    	byte[] bytes = msg.toBytes(); 
    	buf.writeInt(bytes.length); //write Message length
        buf.writeBytes(bytes); //write Message body
    }
}
