package com.lambstat;

import com.lambstat.model.LoginRequest;
import com.lambstat.model.LoginResponse;
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
        UserResource resource = target.proxy(UserResource.class);
        System.out.println("resource: " + resource);

        // call with wrong credentials
        LoginRequest request = new LoginRequest();
        request.setUsername("aaa");
        request.setPassword("111");
        LoginResponse loginResponse = resource.login(request);
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

        // correct credentials
        request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("123");
        loginResponse = resource.login(request);
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

    }

}

