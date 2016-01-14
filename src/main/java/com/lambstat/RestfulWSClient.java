package com.lambstat;

import com.lambstat.model.LoginRequest;
import com.lambstat.model.LoginResponse;
import com.lambstat.model.ShutdownRequest;
import com.lambstat.module.webserver.resource.CameraResource;
import com.lambstat.module.webserver.resource.UserResource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;

public class RestfulWSClient implements Runnable {

    public void run() {
        // define client and target
        ResteasyClient client;
        ResteasyWebTarget target;

        // create 10 async requests
        for (int i = 0; i < 10; i++) {
            // create client and target for each async request
            client = new ResteasyClientBuilder().build();
            target = client.target("http://localhost:9088/").path("/async/ok");

            // create request object
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername("john");
            loginRequest.setPassword("123");

            // do async request with this request object
            target.request().async().post(
                    Entity.entity(loginRequest, MediaType.APPLICATION_JSON),
                    new InvocationCallback<LoginResponse>() {
                        @Override
                        public void completed(LoginResponse loginResponse) {
                            // this is the response,
                            // and it will be called when the server sends response
                            System.out.println(ToStringBuilder.reflectionToString(loginResponse));
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            // if any error / exception occurs, like 405 method allowed message
                            System.out.println("Exception: " + throwable.getMessage());
                        }
                    });
            // these 10 logs will be written immediately
            System.out.println(i + ". async request");
        }

        // create client proxy
        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:9088/");
        UserResource userResource = target.proxy(UserResource.class);

        // call with wrong credentials
        LoginRequest request = new LoginRequest();
        request.setUsername("aaa");
        request.setPassword("111");
        LoginResponse loginResponse = userResource.login(request);
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

        // correct credentials
        request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("123");
        loginResponse = userResource.login(request);
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

        // create client proxy
        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:9088/");
        CameraResource cameraResource = target.proxy(CameraResource.class);

        ShutdownRequest shutdownrequest = new ShutdownRequest();
        shutdownrequest.setImmediately(false);
        String response = cameraResource.cameraTest(shutdownrequest);
        System.out.println("response: " + response);

    }

}

