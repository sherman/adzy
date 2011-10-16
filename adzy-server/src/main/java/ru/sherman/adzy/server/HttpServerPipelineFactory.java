package ru.sherman.adzy.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import static org.jboss.netty.channel.Channels.*;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class HttpServerPipelineFactory implements ChannelPipelineFactory {
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", new HttpRequestHandler());
        return pipeline;
    }
}
