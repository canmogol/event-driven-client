package com.lambstat;

public class Client {

    public static void main(String[] args) {
        new Thread(new JettyClient()).start();
        new Thread(new ZMQClient()).start();
    }

}
