package net.woolgens.library.microservice.auth;

import io.quarkus.runtime.StartupEvent;
import lombok.Getter;
import net.woolgens.library.auth.AuthBootstrap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@ApplicationScoped
@Getter
public class AuthWrapper {

    AuthBootstrap bootstrap;

    public void onStart(@Observes StartupEvent event) {
        try {
            this.bootstrap = new AuthBootstrap("config/");
        }catch (Exception exception){
            System.out.println("There was an error while trying to initialize auth bootstrap");
        }
    }
}
