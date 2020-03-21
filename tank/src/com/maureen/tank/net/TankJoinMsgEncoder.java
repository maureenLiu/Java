package com.maureen.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankJoinMsgEncoder extends MessageToByteEncoder<TankJoinMsg> { //MessageToByteEncoder<TankMsg>:灏員ankMsg娑堟伅杞崲鎴愬瓧鑺�
    @Override
    protected void encode(ChannelHandlerContext ctx, TankJoinMsg msg, ByteBuf buf) throws Exception {
        buf.writeBytes(msg.toBytes()); //msg.toBytes() --获取到msg的字节数组，将该字节数组写到buf中
    }


}
