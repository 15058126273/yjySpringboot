package com.yjy.test.game.util.concurrent;

import java.util.concurrent.BlockingQueue;

import com.yjy.test.game.entity.LoginLog;
import com.yjy.test.game.service.LoginLogService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class ConsumerLoginLog implements Runnable {

    public static boolean running = false;

    protected WebApplicationContext ctx;

    private LoginLogService loginLogService;

    @SuppressWarnings("rawtypes")
    protected BlockingQueue queue = null;

    protected static int i = 0;

    @SuppressWarnings("rawtypes")
    public ConsumerLoginLog(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!queue.isEmpty()) {
                LoginLog loginLog = (LoginLog) queue.take();
                if (loginLog != null) {
                    record(loginLog);
                }
            }
            ConsumerLoginLog.running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void record(LoginLog loginLog) {
        try {
            if (loginLog != null) {
                WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                loginLogService = (LoginLogService) wac.getBean("loginLogService");
                loginLogService.loginLogInsert(loginLog);
                ConsumerLoginLog.i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
