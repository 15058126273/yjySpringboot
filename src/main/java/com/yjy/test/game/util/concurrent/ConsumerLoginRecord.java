package com.yjy.test.game.util.concurrent;

import java.util.concurrent.BlockingQueue;

import com.yjy.test.game.entity.LoginRecord;
import com.yjy.test.game.service.LoginRecordService;


public class ConsumerLoginRecord implements Runnable {

    public static boolean running = false;

    private LoginRecordService loginRecordService;

    @SuppressWarnings("rawtypes")
    protected BlockingQueue queue;

    protected static int i = 0;

    @SuppressWarnings("rawtypes")
    public ConsumerLoginRecord(LoginRecordService loginRecordService, BlockingQueue queue) {
        this.queue = queue;
        this.loginRecordService = loginRecordService;
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
                loginRecordService.loginLogInsert(loginRecord);
                ConsumerLoginRecord.i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
