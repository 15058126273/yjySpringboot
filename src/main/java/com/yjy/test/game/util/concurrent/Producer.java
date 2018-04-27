package com.yjy.test.game.util.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * 处理多线程
 *
 * @author wsc
 * 2016年8月5日
 */
@SuppressWarnings("rawtypes")
public class Producer implements Runnable {

    protected BlockingQueue queue = null;
    protected Object object = null;

    public Producer(BlockingQueue queue, Object object) {
        this.queue = queue;
        this.object = object;
    }


    @SuppressWarnings("unchecked")
    public void run() {
        try {
            if (object != null) {
                queue.put(object);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
