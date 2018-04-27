package com.yjy.test.game.util.concurrent;

import java.util.concurrent.BlockingQueue;

import com.yjy.test.game.entity.Log;
import com.yjy.test.game.service.LogService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 后台日志
 *
 * @author wsc
 * 2016年8月23日
 */
@SuppressWarnings("rawtypes")
public class ConsumerLog implements Runnable {

    public static boolean running = false;
    protected WebApplicationContext ctx;
    private LogService logService;
    protected BlockingQueue queue = null;
    protected static int i = 0;

    public ConsumerLog(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!queue.isEmpty()) {
                Log log = (Log) queue.take();
                if (log != null) {
                    record(log);
                }
            }
            ConsumerLog.running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void record(Log log) {
        try {
            if (log != null) {
                WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
                logService = (LogService) wac.getBean("logService");
                logService.save(log);
                ConsumerLog.i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
