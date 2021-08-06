package com.sqq.demo.demo;

/**
 * @author sqq
 * @Date 2021/7/9
 */
public class B {

    static {
        System.out.println("静态块");
    }

    public static B t1 = new B();
    public static B t2 = new B();

    {
        System.out.println("构造块");
    }



    public static void main(String[] args) {
        B b = new B();
    }
}
