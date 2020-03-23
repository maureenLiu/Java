package com.maureen.tank.net;

import com.maureen.tank.Tank;
import com.maureen.tank.TankFrame;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static final Client INSTANCE = new Client();
	private Channel channel = null;
	
	private Client() {
		
	}

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture f = b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer()).connect("localhost", 8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        System.out.println("Not connected!");
                    } else {
                        System.out.println("connected!");
                        //initialize the channel
                        channel = future.channel(); 
                    }
                }
            });
            f.sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
       channel.writeAndFlush(msg);
    }

    public void closeConnect() {
        //this.send("_bye_"); 
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TankJoinMsgEncoder())
                .addLast(new TankJoinMsgDecoder())
                .addLast(new ClientChannelHandler());
    }
}

class ClientChannelHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		msg.handle();
		
	}
    
}