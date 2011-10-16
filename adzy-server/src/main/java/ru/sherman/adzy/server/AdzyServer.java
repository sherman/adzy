package ru.sherman.adzy.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 16.10.11
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class AdzyServer {
    private static final int SERVER_PORT = 5544;

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(
            new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());

        bootstrap.bind(new InetSocketAddress(SERVER_PORT));
    }
}
