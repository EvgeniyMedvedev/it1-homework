package it1.aop.homework;

import it1.aop.homework.services.ActionSimulator;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@SpringBootApplication
public class Main {
    private final ActionSimulator actionSimulator;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        actionSimulator.anyAsyncDoing();
        actionSimulator.anyDoing();
    }
}
