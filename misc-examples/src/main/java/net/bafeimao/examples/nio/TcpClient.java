package net.bafeimao.examples.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015/10/14.
 */
public class TcpClient {
    private Selector selector;
    private SocketChannel socketChannel;
    private String host;
    private int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        this.socketChannel = SocketChannel.open(new InetSocketAddress(host, port));

        this.socketChannel.configureBlocking(false);

        // Open selector
        this.selector = Selector.open();

        // Register client channel to selector
        this.socketChannel.register(selector, SelectionKey.OP_READ);

        Processor process = new Processor(selector);
        process.start();
    }

    class Processor extends Thread {
        private Selector selector;

        public Processor(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (selector.select() > 0) {
                    // ����ÿ���п���IO����Channel��Ӧ��SelectionKey
                    for (SelectionKey selectionKey : selector.selectedKeys()) {
                        if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            socketChannel.read(buffer);

                            buffer.flip();

                            String receivedString = Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
                            System.out.println("RECEIVED: " + receivedString + ", from:" + socketChannel.socket().getRemoteSocketAddress());

                            // Ϊ��һ�ζ�ȡ��׼��
                            selectionKey.interestOps(SelectionKey.OP_READ);
                        }

                        // ɾ�����ڴ����SelectionKey
                        selector.selectedKeys().remove(selectionKey);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

