package week3.nio02.src.main.java.io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import week3.nio02.src.main.java.io.github.kimmking.gateway.filter.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
public class HttpRequestFilterTest implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", "1");
        map.put("username", "jack");
        String token = Base64.getEncoder().encodeToString(map.toString().getBytes());
        String uri = fullRequest.uri();
        System.err.println("uri=="+uri);
        fullRequest.headers().set("Authorization", token);
    }
}
