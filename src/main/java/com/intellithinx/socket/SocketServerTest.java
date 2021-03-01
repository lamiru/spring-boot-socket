package com.intellithinx.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class SocketServerTest {
    private static Logger log = LoggerFactory.getLogger(SocketServerTest.class);
    private static ServerSocket server;
    private static int port = 9876;

    public static void timeDelay(long t) {
        try {
            java.lang.Thread.sleep(t);
        } catch (InterruptedException e) {
            e.getStackTrace();
            log.error(e.toString());
        }
    }

    public static void main(String[] args) {
        try {
            server = new ServerSocket(port);
            Socket socket = server.accept();
            OutputStream socketOutputStream = socket.getOutputStream();
            InputStream socketInputStream = socket.getInputStream();
            while (true) {
                byte[] receivedByte = new byte[100];
                int readSize = socketInputStream.read(receivedByte);
                String receivedString = new String(receivedByte, StandardCharsets.UTF_8).trim();
                if (readSize > 0) {
                    log.info("Socket message is received: " + receivedString);
                    String message = "<{\"block\":\"3AL\",\"truck\":\"OK\"}>";
                    OutputStreamWriter osw = new OutputStreamWriter(socketOutputStream, StandardCharsets.UTF_8);
                    osw.write(message, 0, message.length());
                    osw.flush();
                    log.info("Socket message is sent: " + message);
                    timeDelay(100);
                }
                if (receivedString.contains("12341234")) break;
            }
            socketOutputStream.close();
            socketInputStream.close();
            server.close();
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.toString());
        }
    }
}
