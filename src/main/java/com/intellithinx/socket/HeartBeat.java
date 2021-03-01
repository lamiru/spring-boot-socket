package com.intellithinx.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public class HeartBeat {
    private static Logger log = LoggerFactory.getLogger(HeartBeat.class);
    private static String host = "127.0.0.1";
    private static int port = 9876;

    public static void timeDelay(long t) {
        try {
            java.lang.Thread.sleep(t);
        } catch (InterruptedException e) {
            e.getStackTrace();
            log.error(e.toString());
        }
    }

    public static void run() {
        try {
            Socket socket = new Socket();
            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address);
            OutputStream socketOutputStream = socket.getOutputStream();
            InputStream socketInputStream = socket.getInputStream();
            for (int i = 0; i < 5; i++) {
                String message = "<{\"block\":\"3AL\",\"truck\":\"부산99가1234\"}>".trim();
                OutputStreamWriter osw = new OutputStreamWriter(socketOutputStream, StandardCharsets.UTF_8);
                osw.write(message, 0, message.length());
                osw.flush();
                log.info("Sent socket message: " + message);

                byte[] receiveMessage = new byte[100];
                int readSize = socketInputStream.read(receiveMessage);
                String receivedMessage = new String(receiveMessage, StandardCharsets.UTF_8).trim();
                if (readSize > 0) {
                    log.info("Received socket message: " + receivedMessage);
                }
                timeDelay(1000);
            }
            socketOutputStream.close();
            socketInputStream.close();
            socket.close();
            String isClosed = socket.isClosed() ? "Closed" : "Connected";
            log.info(isClosed);
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.toString());
        }
    }
}
