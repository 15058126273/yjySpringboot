package com.yjy.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 16:09
 */
@Component
@Order(value = 2)
public class MyCommandLineRunner2 implements CommandLineRunner {

    public void run(String... args) {
        System.out.println("2:" + Arrays.toString(args));
    }

}
