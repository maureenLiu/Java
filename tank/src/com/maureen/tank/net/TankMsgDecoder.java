package com.maureen.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8)  //鑷冲皯8涓瓧鑺傦紝涓嶅埌8涓瓧鑺傚氨娌¤鍏紝灏变笉杩涜澶勭悊銆傝В鍐充簡TCP鎷嗗寘 绮樺寘鐨勯棶棰�
            return;
        ;

        //in.markReaderIndex();

        int x = in.readInt();
        int y = in.readInt();

        out.add(new TankMsg(x, y));
    }
}
