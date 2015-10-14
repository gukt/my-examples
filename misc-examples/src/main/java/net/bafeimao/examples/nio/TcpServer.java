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
                        System.out.println("�пͻ����������ӡ�����");
                    }

                    if(key.isReadable()) {
                        System.out.println("�ͻ���������");
                    }

                    if(key.isValid() && key.isWritable()) {
                        System.out.println("�����ݷ��ظ��ͻ���");
                    }
                } catch (Exception e) {

                }
            }
        }
    }


    public static void main1(String[] args) throws IOException {

//        while (true) {
//            // �ȴ�ĳ�ŵ�����(��ʱ)
//            if (selector.select(TimeOut) == 0) {// ����ע��ͨ������������ע��� IO
//                // �������Խ���ʱ���ú������أ�������Ӧ��
//                // SelectionKey ���� selected-key
//                // set
//                System.out.print("���Եȴ�.");
//                continue;
//            }
//            // ȡ�õ�����.selectedKeys()�а�����ÿ��׼����ĳһI/O�������ŵ���SelectionKey
//            // Selected-key Iterator ����������ͨ�� select() ������⵽���Խ��� IO ������ channel
//            // ��������Ͽ���ͨ�� selectedKeys() �õ�
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
//                    // ����IO�쳣����ͻ��˶Ͽ����ӣ�ʱ�Ƴ�������ļ�
//                    keyIter.remove();
//                    continue;
//                }
//                // �Ƴ�������ļ�
//                keyIter.remove();
//            }
//        }
    }
}