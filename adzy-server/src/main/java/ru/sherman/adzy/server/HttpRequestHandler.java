package ru.sherman.adzy.server;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;
import ru.sherman.adzy.advertisement.Banner;
import ru.sherman.adzy.algorithm.BannerSelector;
import ru.sherman.adzy.algorithm.WeightedUniformDistributionBasedBannerSelector;
import ru.sherman.adzy.util.Interval;
import ru.sherman.adzy.util.IntervalTree;

import java.util.Arrays;
import java.util.Set;

import static org.jboss.netty.handler.codec.http.HttpHeaders.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.*;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestHandler extends SimpleChannelUpstreamHandler {
    private static final Logger log = Logger.getLogger(HttpRequestHandler.class);

    private HttpRequest request;
    private final StringBuilder out = new StringBuilder();

    // FIXME: must be injected and updatable
    private final BannerSelector bannerSelector = new WeightedUniformDistributionBasedBannerSelector(
        new IntervalTree<Banner>(
            Arrays.<Interval>asList(
                new Interval(1, 100, new Banner(1, 100)),
                new Interval(101, 200, new Banner(2, 100)),
                new Interval(201, 500, new Banner(3, 300))
            )
        )
    );

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        //log.debug("Msg received");
        HttpRequest request = this.request = (HttpRequest) e.getMessage();

        Banner banner = bannerSelector.getBanner();
        out.append("Banner id is:" + banner.getId());

        // do work here
        ChannelBuffer content = request.getContent();
        if (content.readable()) {
            out.append("CONTENT: " + content.toString(CharsetUtil.UTF_8) + "\r\n");
        }
        writeResponse(e);
    }

    public void writeResponse(MessageEvent e) {
        // Decide whether to close the connection or not.
        boolean keepAlive = isKeepAlive(request);

        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        response.setContent(ChannelBuffers.copiedBuffer(out.toString(), CharsetUtil.UTF_8));
        response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
        }

        // Encode the cookie.
        String cookieString = request.getHeader(COOKIE);
        if (cookieString != null) {
            CookieDecoder cookieDecoder = new CookieDecoder();
            Set<Cookie> cookies = cookieDecoder.decode(cookieString);

            if (!cookies.isEmpty()) {
                // Reset the cookies if necessary.
                CookieEncoder cookieEncoder = new CookieEncoder(true);
                for (Cookie cookie : cookies) {
                    cookieEncoder.addCookie(cookie);
                }
                response.addHeader(SET_COOKIE, cookieEncoder.encode());
            }
        }

        // Write the response.
        ChannelFuture future = e.getChannel().write(response);
        // Close the non-keep-alive connection after the write operation is done.
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        log.error("Exception occured in http handler", e.getCause());
        e.getChannel().close();
    }
}
