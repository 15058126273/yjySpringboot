package com.yjy.test.game.util.concurrent;

import java.util.concurrent.BlockingQueue;

import com.yjy.test.game.entity.LoginRecord;
import com.yjy.test.game.service.LoginRecordService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class ConsumerLoginRecord implements Runnable {

    public static boolean running = false;

    protected WebApplicationContext ctx;

    private LoginRecordService loginRecordService;

    @SuppressWarnings("rawtypes")
    protected BlockingQueue queue = null;

    protected static int i = 0;

    @SuppressWarnings("rawtypes")
    public ConsumerLoginRecord(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!queue.isEmpty()) {
                LoginRecord loginRecord = (LoginRecord) queue.take();
                if (loginRecord != null) {
                    record(loginRecord);
                }
            }
            ConsumerLoginRecord.running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void record(LoginRecord loginRecord) {
        try {
            if (loginRecord != null) {
                WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                loginRecordService = (LoginRecordService) wac.getBean("loginRecordService");
                loginRecordService.loginLogInsert(loginRecord);
                ConsumerLoginRecord.i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
