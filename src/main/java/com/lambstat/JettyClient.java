package com.lambstat;

import com.lambstat.module.jetty.data.LoginResponse;
import com.lambstat.module.jetty.resource.JettyResource;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JettyClient implements Runnable {
    @Override
    public void run() {
        doRSCall();
    }

    private void doRSCall() {
        JettyResource resource = Resources.create(
                JettyResource.class,
                "http://localhost:8080/"
        );
        this.call(resource);
    }

    private void call(JettyResource resource) {
        System.out.println("resource: " + resource);
        // call with wrong credentials
        LoginResponse loginResponse = resource.login("wrong", "credentials");
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

        // correct credentials
        loginResponse = resource.login("john", "123");
        System.out.println(ToStringBuilder.reflectionToString(loginResponse));

    }
}
