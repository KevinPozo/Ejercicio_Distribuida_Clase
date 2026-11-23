package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthorLifecycle {
    @Inject
    @ConfigProperty(name = "conul.host" , defaultValue = "127.0.0.1")
    String consulHost;

    @Inject
    @ConfigProperty(name = "conul.port",defaultValue = "8500")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port",defaultValue = "8070")
    Integer appPort;

    @Inject
    @ConfigProperty(name = "app.name")
    String appName;

public void init(@Observes StartupEvent event, Vertx vertx){
    System.out.println("AuthorLifecycle.init() called");
    try {
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);

        ConsulClient client = ConsulClient.create(vertx,options);
        String ipAddress = "127.0.0.1";

        String serviceID = "%s-%s:%d".formatted(appName,appPort);

        ServiceOptions serviceOptions = new ServiceOptions()
                .setName(appName)
                .setAddress(ipAddress)
                .setPort(appPort);

        client.registerService(serviceOptions)
                .onSuccess(it-> System.out.println("Service registered with consul: "+serviceID))
                .onFailure(it-> System.out.println("Service registration failed: "+it.getMessage()));

    }catch (Exception e){
        e.printStackTrace();
    }
}
public void destroy(@Observes ShutdownEvent event, Vertx vertx){
    System.out.println("AuthorLifecycle.destroy() called");
}

}
