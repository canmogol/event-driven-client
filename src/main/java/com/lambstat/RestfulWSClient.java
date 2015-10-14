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

public class RestfulWSClient implements Runnable {
    @Override
    public void run() {

        // create client proxy
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/");
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

