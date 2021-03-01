package com.intellithinx;

import com.intellithinx.socket.HeartBeat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        HeartBeat.run();
    }

}
