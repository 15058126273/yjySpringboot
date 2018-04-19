package com.yjy.test.service;

import org.springframework.stereotype.Service;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-17 15:53
 */
@Service
public class IndexService {

    public void print(String arg) {
        System.out.printf("IndexService...doing something, arguments: %s \n", arg);
    }

}
