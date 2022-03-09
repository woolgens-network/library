package net.woolgens.library.web.route;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import net.woolgens.library.web.server.WrappedWebServer;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface WebRoute extends Handler<RoutingContext> {

    String getPath();
    WrappedWebServer getServer();
}
