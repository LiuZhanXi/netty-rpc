package zhanxi.netty.ws;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname ChatHandler
 * @Description 消息处理类
 * @Date 2022/4/9 11:37
 * @Email zhanxi.liu@seaboxdata.com
 * @Author liuzhanxi
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	//用来保存所有的客户端连接
	private static final ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	//创建一个时间生成器
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM");

	@Override //该方面当接收到数据时候会自动调用
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String text = msg.text();
		log.info("接收到的消息为:{}", text);

		//遍历clients(所有客户端,群发)
		for (Channel client : clients) {
			//发送消息并刷新通道
			client.writeAndFlush(new TextWebSocketFrame(sdf.format(new Date()) + ": " + "hello Netty~"));
		}
	}

	@Override   //当有新的客户端接入到服务器时候会自动调用该方法
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		clients.add(ctx.channel());//将新的连接加入channel中
	}
}