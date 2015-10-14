package net.bafeimao.examples.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/13.
 */
public class NioTests {
    private static Logger LOGGER = LoggerFactory.getLogger(NioTests.class);
    private static final String HOST = "localhost";
    private static final int PORT = 3304;

    @Test
    public void testReadFromFileByFileChannel() throws IOException {
        RandomAccessFile file = new RandomAccessFile("c:/111.txt", "rw");
        FileChannel inChannel = file.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        file.close();
    }


    @Test
    public void testServerSocketChannelAccept() throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(HOST, PORT));

        // 将这里分别设置为true(默认）和false，看看accept方法如何执行
        // ssc.configureBlocking(false);

        // NOTE: 可用Telnet连接测试

        while (true) {
            // 如果ssc是阻塞模式，那么accept会一直阻塞，反之立即返回null
            SocketChannel socketChannel = ssc.accept();

            if (socketChannel == null) {
                TimeUnit.SECONDS.sleep(1);
            } else {
                System.out.println("A client was connected!!!");
            }
        }
    }

    @Test
    public void testSocketChannelConnect() throws IOException {
        SocketChannel socketChannel = null;

        try {
            // 打开一个客户端SocketChannel
            socketChannel = SocketChannel.open();

            // 连接服务器
            socketChannel.connect(new InetSocketAddress(HOST, PORT));
        } finally {
            if(socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
        }
    }

    @Test
    public void testSocketChannelWriteOp() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(HOST, PORT));

        socketChannel.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(32);
        buf.put("Hey man!!!".getBytes());

        buf.flip();

        // 由于write()方法无法保证能写多少字节到SocketChannel,因此这里放到while循环体中
        while(buf.hasRemaining()) {
            socketChannel.write(buf);
        }

        System.out.println("Successfully written the data to channel");
    }
    @Test
    public void testSocketChannelReadOp() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(HOST, PORT));

        ByteBuffer buf = ByteBuffer.allocate(32);

        // 从channel中读取数据到buffer中
        socketChannel.read(buf);
    }
}
