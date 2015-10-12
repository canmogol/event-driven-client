package com.lambstat;

import com.lambstat.rws.JettyClient;
import com.lambstat.zmq.ZMQClient;

public class Client {

    public static void main(String[] args) {
        new Thread(new JettyClient()).start();
        new Thread(new ZMQClient()).start();
    }

}
