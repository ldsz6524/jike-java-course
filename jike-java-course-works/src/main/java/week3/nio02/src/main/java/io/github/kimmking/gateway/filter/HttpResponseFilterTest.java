package week3.nio02.src.main.java.io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

import java.util.UUID;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
public class HttpResponseFilterTest implements io.github.kimmking.gateway.filter.HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("sign", UUID.randomUUID().toString());
    }
}
