package com.programacion.distribuida.authors.rest;

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

}
