package com.lambstat;

public class Client {

    public static void main(String[] args) {
        new Thread(new RestfulWSClient()).start();
        new Thread(new ZMQClient()).start();
        new Thread(new ZMQJavaClient()).start();
    }

}
