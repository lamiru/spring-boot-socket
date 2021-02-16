package com.intellithinx.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Service
public class HeartBeat {
    private static Logger log = LoggerFactory.getLogger(HeartBeat.class);
    private static String host = "127.0.0.1";
    private static int port = 9876;

    @Scheduled(fixedRate=3000)
    public static void run() {
        try {
            Socket socket = new Socket(host, port);
            OutputStream socketOutputStream = socket.getOutputStream();
            InputStream socketInputStream = socket.getInputStream();

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
            socketOutputStream.close();
            socketInputStream.close();
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.toString());
        }
    }
}
