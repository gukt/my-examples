package net.bafeimao.examples.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/10/14.
 */
public class TcpServer {
    private static final int BUFFER_SIZE = 1024;

    private static final int TIMEOUT = 3000;

    private static final int PORT = 3001;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(PORT));
        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
                selector.select(TIMEOUT);
                SelectionKey key = it.next();

                try {
                    if (key.isAcceptable()) {
                        System.out.println("有客户端正在连接。。。");
                    }

                    if(key.isReadable()) {
                        System.out.println("客户端有请求");
                    }

                    if(key.isValid() && key.isWritable()) {
                        System.out.println("有数据返回给客户端");
                    }
                } catch (Exception e) {

                }
            }
        }
    }


    public static void main1(String[] args) throws IOException {

//        while (true) {
//            // 等待某信道就绪(或超时)
//            if (selector.select(TimeOut) == 0) {// 监听注册通道，当其中有注册的 IO
//                // 操作可以进行时，该函数返回，并将对应的
//                // SelectionKey 加入 selected-key
//                // set
//                System.out.print("独自等待.");
//                continue;
//            }
//            // 取得迭代器.selectedKeys()中包含了每个准备好某一I/O操作的信道的SelectionKey
//            // Selected-key Iterator 代表了所有通过 select() 方法监测到可以进行 IO 操作的 channel
//            // ，这个集合可以通过 selectedKeys() 拿到
//            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
//            while (keyIter.hasNext()) {
//                SelectionKey key = keyIter.next();
//                SelectionKey key1;
//                if (keyIter.hasNext()) {
//                    key1 = keyIter.next();
//                }
//                try {
//
//                } catch (IOException ex) {
//                    // 出现IO异常（如客户端断开连接）时移除处理过的键
//                    keyIter.remove();
//                    continue;
//                }
//                // 移除处理过的键
//                keyIter.remove();
//            }
//        }
    }
}