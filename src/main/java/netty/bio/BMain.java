package netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author hudi
 * @Date 2018/12/22 16:58
 * @Version 1.0
 **/
public class BMain {

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ServerSocket serverSocket = new ServerSocket(1111);
        while (true) {
            final Socket accept = serverSocket.accept();
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    private static void handler(Socket accept) {
        InputStream inputStream = null;
        try {
            inputStream = accept.getInputStream();
        } catch (Exception e) {

        }
        byte[] bytes = new byte[1024];

        try {
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes));
                } else {
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                accept.close();
            } catch (Exception e) {

            }
        }

    }
}


