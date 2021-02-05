package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            String uri = ((HttpRequest) msg).uri();
            System.out.println(uri);
            if (uri.contains("/favicon.ico")){
                System.out.println("不相应");
                return;
            }
            System.out.println("地址：" + ctx.channel().remoteAddress());

            ByteBuf context = Unpooled.copiedBuffer("hello,大帅比", UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,context.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
