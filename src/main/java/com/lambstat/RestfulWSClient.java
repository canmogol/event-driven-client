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

import javax.ws.rs.client.InvocationCallback;

public class RestfulWSClient implements Runnable {

    @Override
    public void run() {
        ResteasyClient client;
        ResteasyWebTarget target;

        for (int i = 0; i < 10; i++) {
            client = new ResteasyClientBuilder().build();
            target = client.target("http://localhost:8080/").path("/async/ok");

            target.request().async().get(new InvocationCallback<Object>() {
                @Override
                public void completed(Object o) {
                    System.out.println(o);
                }

                @Override
                public void failed(Throwable throwable) {
                    System.out.println(throwable);
                }
            });

            System.out.println(i + ". async request");
        }

        // create client proxy
        client = new ResteasyClientBuilder().build();
        target = client.target("http://localhost:8080/");
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
        target = client.target("http://localhost:8080/");
        CameraResource cameraResource = target.proxy(CameraResource.class);

        ShutdownRequest shutdownrequest = new ShutdownRequest();
        shutdownrequest.setImmediately(false);
        String response = cameraResource.cameraTest(shutdownrequest);
        System.out.println("response: " + response);

    }

}

