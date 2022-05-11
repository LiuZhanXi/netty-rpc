package zhanxi.netty.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname WebSocketNettyServer
 * @Description 基础配置类
 * @Date 2022/4/9 11:37
 * @Email zhanxi.liu@seaboxdata.com
 * @Author liuzhanxi
 */
@Slf4j
public class WebSocketNettyServer {
	public static void main(String[] args) {
		int port = 9090;
		//创建netty的主从两个线程池
		NioEventLoopGroup mainGrp = new NioEventLoopGroup();//主线程池
		NioEventLoopGroup subGrp = new NioEventLoopGroup();//从线程池
		try {
			//创建Netty服务器启动对象
			ServerBootstrap serverBootstrap = new ServerBootstrap();

			//初始化服务器启动对象
			serverBootstrap
					//为netty服务器指定和配备主从线程池
					.group(mainGrp, subGrp)
					//指定netty通道类型
					.channel(NioServerSocketChannel.class)
					//指定通道初始化器用来加载当channel收到消息后
					//如何进行业务处理
					.childHandler(new WebSocketChannelInitializer());
			log.info("创建Bootstrap成功");

			//绑定服务器端口, 以同步的方式启动服务器
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			if (!channelFuture.isSuccess()){
				log.info("服务启动失败,无法绑定端口:{}", port);
				throw new IllegalArgumentException("服务启动失败,无法绑定端口{}" + port);
			}
			log.info("服务启动成功，当前绑定端口:{}", port);
			//等待服务器关闭
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//使用优雅的方式关闭服务器
			//主要是关闭两个线程池
			mainGrp.shutdownGracefully();
			subGrp.shutdownGracefully();
		}
	}
}
