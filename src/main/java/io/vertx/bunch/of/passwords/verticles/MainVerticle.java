package io.vertx.bunch.of.passwords.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {
    Router router = Router.router(vertx);

    vertx.deployVerticle(KeyPassVerticle.class.getName());

    router.get("/").handler(handler -> {
      handler.response().setStatusCode(200).end("Hello World");
    });

    router.get("/keys").handler(handler -> {
      vertx.eventBus().send("key-pass", "getKeys", messageHandler -> {
        if (messageHandler.succeeded()) {
          handler.response().setStatusCode(200).end(messageHandler.result().body().toString());
        } else {
          handler.response().setStatusCode(500).end("INTERNALE SERVER ERROR");
        }
      });
    });

    router.post("/keys").handler(handler -> {
      vertx.eventBus().send("key-pass", "postKey", new DeliveryOptions().
        addHeader("login", handler.request().getHeader("login"))
        .addHeader("password", handler.request().getHeader("password"))
        .addHeader("link", handler.request().getHeader("link"))
        .addHeader("lastEdit", handler.request().getHeader("lastEdit")), messageHandler -> {
        if (messageHandler.succeeded()) {
          handler.response().setStatusCode(200).end(messageHandler.result().body().toString());
        } else {
          handler.response().setStatusCode(500).end("INTERNALE SERVER ERROR");
        }
      });

    });

    vertx.createHttpServer().requestHandler(router::accept).listen(8080, listenHandler -> {
      logger.info("listing on Port 8080");
    });
  }
}
