package com.example.test.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT_NAME: Test
 * @DESCRIPTION:
 * @USER: wusiyu
 * @DATE: 2021/3/5 17:56
 */
public class Test {
    private int start = 0;
    public static void main(String[] args) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            res.add(new Test().test());
        }
        System.out.println(res);
    }
    private int test() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                add();
            }
        };
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return start;
    }
    void add() {
        for (int i = 0; i < 10; i++) {
            start++;
        }
    }
}
