package com.spring.binar.challenge_5.scheduler;

import com.spring.binar.challenge_5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class TokenRemoveScheduler {

    private final UserService userService;

    @Scheduled(cron = "20 * * * * *")
    public void removeTokenUser(){
        log.info("Scheduler started..");
        userService.deleteTokenWhenExpired();
        log.info("Scheduler finish..");
    }



}
