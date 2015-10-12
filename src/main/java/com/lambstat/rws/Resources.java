package com.lambstat.rws;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class Resources<T> {

    private static Resources instance = null;

    private Resources() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> t, String url) {
        return (T) getInstance().createResource(t, url);
    }

    private T createResource(Class<T> t, String url) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(url);
        return target.proxy(t);
    }

    private static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }

}

