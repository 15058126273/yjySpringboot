package com.yjy.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
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
@Order(value = 1)
public class MyCommandLineRunner implements CommandLineRunner {

    public void run(String... args) {
        System.out.println("1:" + Arrays.toString(args));
    }

}
