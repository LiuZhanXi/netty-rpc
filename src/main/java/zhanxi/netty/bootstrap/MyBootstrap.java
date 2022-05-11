package zhanxi.netty.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Classname MyBootstrap
 * @Description
 * @Date 2022/5/10 17:13
 * @Email zhanxi.liu@seaboxdata.com
 * @Author liuzhanxi
 */
@Slf4j
public class MyBootstrap {

//	public static void main(String[] args) {
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//		ServerBootstrap server = new ServerBootstrap();
//		server.group(bossGroup, workerGroup)
//				.channel(NioServerSocketChannel.class);
//
//		server.bind(9091).addListener(future -> {
//			if (future.isSuccess()) {
//				log.info(new Date() + ": 端口[" + 9091 + "]绑定成功!");
//			} else {
//				log.info("端口[" + 9091 + "]绑定失败!");
//			}
//		});
//	}

	public static void main(String[] args) {
		// 创建mainReactor
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();
		// 创建工作线程组
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		final ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap
				// 组装NioEventLoopGroup
				.group(boosGroup, workerGroup)
				// 设置channel类型为NIO类型
				.channel(NioServerSocketChannel.class)
				// 设置连接配置参数
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				// 配置入站、出站事件handler
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) {
						// 配置入站、出站事件channel
						ch.pipeline().addLast();
					}
				});

		// 绑定端口
		int port = 8080;
		serverBootstrap.bind(port).addListener(future -> {
			if (future.isSuccess()) {
				System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
			} else {
				System.err.println("端口[" + port + "]绑定失败!");
			}
		});
	}
}
