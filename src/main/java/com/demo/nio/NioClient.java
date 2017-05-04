package com.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NioClient(int port) {
        init(port);
    }

    public SocketChannel getChannel() {
        return channel;
    }

    private void init(final int port) {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(port));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        while (true) {
            selector.select(1000);
            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                process(key);
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (sc.isConnectionPending()) {
                System.out.println("等待连接...");
                sc.finishConnect();
            }
            sc.register(this.selector, SelectionKey.OP_READ);
            sc.write(ByteBuffer.wrap("我是客户端, 我连接上来了.".getBytes("UTF-8")));
        } else if (key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            int count = sc.read(buffer);
            if (count < 0) {
                key.channel();
                sc.close();
                return;
            }
            buffer.flip();
            String msg = new String(buffer.array(), 0, count);
            System.out.println("Client received [" + msg + "] from server");
            buffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        NioClient client = new NioClient(6666);
        client.connect();
    }
}
