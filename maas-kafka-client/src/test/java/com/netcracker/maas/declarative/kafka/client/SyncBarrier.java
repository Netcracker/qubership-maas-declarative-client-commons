package com.netcracker.maas.declarative.kafka.client;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class SyncBarrier {
    private final ConcurrentMap<String, CountDownLatch> latches = new ConcurrentHashMap<>();

    public void notify(String eventName) {
        log.info("Notify: {}", eventName);
        latches.computeIfAbsent(eventName, k -> new CountDownLatch(1)).countDown();
    }

    public void await(String eventName, Duration timeout) {
        log.info("Await: {} (timeout: {})", eventName, timeout);
        var latch = latches.computeIfAbsent(eventName, k -> new CountDownLatch(1));
        try {
            if (!latch.await(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
                fail("timeout waiting event: " + eventName);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Event received: {}", eventName);
    }
}
