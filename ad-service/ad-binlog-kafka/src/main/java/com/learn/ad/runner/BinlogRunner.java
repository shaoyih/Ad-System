package com.learn.ad.runner;

import com.learn.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {
    private final BinlogClient binlogClient;


    public BinlogRunner(BinlogClient binlogClient) {
        this.binlogClient = binlogClient;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Coming in BinlogRunner");
        binlogClient.connect();
    }
}
