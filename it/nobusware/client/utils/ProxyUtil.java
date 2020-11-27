package it.nobusware.client.utils;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.Channel;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.Socket;

public class ProxyUtil {
	private static Proxy proxy;

	public static void setProxy(Proxy proxy) {
		ProxyUtil.proxy = proxy;
	}

	public static Proxy getProxy() {
		return proxy;
	}

	public static ChannelFactory<OioSocketChannel> createProxyChannel() {
		return new SocketFactory();
	}

	private static class SocketFactory implements ChannelFactory<OioSocketChannel> {
		private SocketFactory() {
		}

		public OioSocketChannel newChannel() {
			if (ProxyUtil.getProxy() == null || ProxyUtil.getProxy() == Proxy.NO_PROXY)
				return new OioSocketChannel(new Socket(Proxy.NO_PROXY));
			Socket sock = new Socket(ProxyUtil.getProxy());
			try {
				Method m = sock.getClass().getDeclaredMethod("getImpl", new Class[0]);
				m.setAccessible(true);
				Object sd = m.invoke(sock, new Object[0]);
				m = sd.getClass().getDeclaredMethod("setV4", new Class[0]);
				m.setAccessible(true);
				m.invoke(sd, new Object[0]);
				return new OioSocketChannel(sock);
			} catch (Exception ex2) {
				throw new RuntimeException("Failed to create socks 4 proxy!", new Exception());
			}
		}
	}
}
