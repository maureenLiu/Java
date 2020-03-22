package com.maureen.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap sb = new ServerBootstrap();
            ChannelFuture future = sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer())
                    .bind(8888)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server started!");

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Server().serverStart();
    }
}

class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();
//        pl.addLast(new TankJoinMsgDecoder()) 
//                .addLast(new ServerChannelHandler());
        pl.addLast(new ServerChannelHandler());
    }
}

class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { 
        Server.clients.writeAndFlush(msg);

//        ByteBuf buf = (ByteBuf)msg;
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.getBytes(buf.readerIndex(), bytes);
//        String s = new String(bytes);
//
//        ServerFrame.INSTANCE.updateClientMsg(s);
//
//        if(s.equals("_bye_")) {
//            ServerFrame.INSTANCE.updateServerMsg("瀹㈡埛绔姹傞��鍑�");
//            Server.clients.remove(ctx.channel());
//            ctx.close();
//        } else {
//            Server.clients.writeAndFlush(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.clients.remove(ctx.channel());
        ctx.close(); 
    }
}