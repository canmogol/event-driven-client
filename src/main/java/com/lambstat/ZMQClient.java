package com.lambstat;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lambstat.model.LambstatModels;
import org.zeromq.ZMQ;

public class ZMQClient implements Runnable {

    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REQ);

        // connect to zmq server
        socket.connect("tcp://localhost:9666");

        // create model, set fields
        LambstatModels.loginRequest loginRequest =
                LambstatModels.loginRequest.newBuilder()
                        .setUsername("john")
                        .setPassword("123")
                        .build();
        System.out.println("sending login request: " + loginRequest.getUsername());
        byte[] bytes =  loginRequest.toByteArray();

        // send bytes
        socket.send("LOGIN_REQUEST", ZMQ.SNDMORE);
        socket.send(bytes);

        // get server's response
        byte[] responseBytes = socket.recv();

        // read login response
        try {
            LambstatModels.loginResponse loginResponse = LambstatModels.loginResponse.parseFrom(responseBytes);
            System.out.println("is logged: " + loginResponse.getLogged());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        // close and exit
        socket.close();
        context.term();
    }

}
