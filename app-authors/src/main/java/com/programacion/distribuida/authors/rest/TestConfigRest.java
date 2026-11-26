package com.programacion.distribuida.authors.rest;

import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.Service;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.Optional;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/config")
public class TestConfigRest {

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer port;

    @ConfigProperty(name = "app.name")
    String appName;

    @Inject
    Vertx vertx;

    @GET
    public String test() {
        // punto de acceso al sistema de configuracion
        Config config = ConfigProvider.getConfig();
        // Listas las fuentes de configuracion
        config.getConfigSources().forEach(source -> {
            System.out.printf("[%5d] %s%n", source.getOrdinal(), source.getName());
        });

        Optional<String> appTitle = config.getOptionalValue("app.title", String.class);
        // Recuperar un valor de configuracion
        String appName = config.getValue("app.name", String.class);
        System.out.println("-------------------------------");
        System.out.println("app.name: " + appName);
        System.out.println("quarkus.http.port: " + port);
        System.out.println("App Name" + appName);
        if (appTitle.isPresent()) {
            System.out.println("app.title: " + appTitle.get());
        } else {
            System.out.println("app.title: No encontrado");
        }
        return "";
    }

    @GET
    @Path("/test2")
    public String test2() {
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost("127.0.0.1")
                .setPort(8500);

        ConsulClient client = ConsulClient.create(vertx, options);

        var response = client.catalogServiceNodes("app-authors");
        response.onComplete(it->{
            if(it.succeeded()){
                System.out.println("found: "+it.result().getList().size()+" services");
                for (Service service: it.result().getList()){
                    System.out.println("Service id: " + service.getId());
                    System.out.println("Service name: " + service.getName());
                    System.out.println("Service node: " + service.getNode());
                    System.out.println("Service address: " + service.getAddress());
                    System.out.println("Service port: " + service.getPort());
                }
            }
        });
        return "";
    }
}
