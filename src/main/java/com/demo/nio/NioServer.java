package com.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    private Set<SocketChannel> channels = new HashSet<>();
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NioServer(int port) {
        init(port);
    }

    private void init(final int port) {
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));

            this.selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws IOException {
        System.out.println("start listen...");
        while (true) {
            look();
            if (selector.select(3000) == 0) {
                System.out.print("监听等待着...");
                continue;
            }
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel sc = ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);

            System.out.println("客户端的地址是 " + sc.socket().getRemoteSocketAddress() + ":" + sc.socket().getLocalPort());
            channels.add(sc);
        } else if (key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(buffer);
            System.out.println("Data: count = " + count);
            if (count < 0) {
                key.channel();
                sc.close();
                return;
            }
            buffer.flip();
            System.out.println("Data: content = " + new String(buffer.array(), 0, count));
            buffer.clear();
            sc.write(ByteBuffer.wrap("我收到信息了, 给你一个回应.".getBytes("utf-8")));
        } else if (key.isWritable()) {
            //当注册写事件之后,只要buffer是有空闲空间就会被触发
            System.out.println("触发了写事件, 先睡三秒.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SocketChannel sc = (SocketChannel) key.channel();
            sc.write(ByteBuffer.wrap("写事件怎么触发?".getBytes("utf-8")));
        }
    }

    public void look() {
        System.out.println(channels.size());
//        channels.forEach(c -> {
//            System.out.println("isOpen:" + c.isOpen());
//            System.out.println("isConnected:" + c.isConnected());
//            System.out.println("isConnectedPending:" + c.isConnectionPending());
//            System.out.println("isRegister:" + c.isRegistered());
//            System.out.println("isBolcking:" + c.isBlocking());
//            try {
//                System.out.println("isFinished:" + c.finishConnect());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(6666);
        server.listen();

    }
}
