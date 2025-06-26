package org.example.onlinecontact;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineContactApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OnlineContactApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 阻塞主线程，防止退出
        Thread.currentThread().join();
    }
}