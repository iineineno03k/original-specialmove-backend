package com.example.originalspecialmove.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 12 * 60 * 1000) // 12 minutes in milliseconds
    public void performTask() {
        System.out.println("定期実行タスクです。");
    }
}
