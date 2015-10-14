package net.bafeimao.examples.nio;

import java.io.IOException;

/**
 * Created by Administrator on 2015/10/14.
 */
public class TcpDemo {
    public static void main(String[] args) throws IOException {
        TcpClient client = new TcpClient("localhost",3304);
        client.connect();


    }
}
