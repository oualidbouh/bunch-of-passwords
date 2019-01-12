package io.vertx.bunch.of.passwords;


import io.vertx.bunch.of.passwords.config.Config;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public class KeyPassVerticle extends AbstractVerticle {

  Logger logger = LoggerFactory.getLogger(KeyPassVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {

    MongoClient mongoClient = MongoClient.createShared(vertx, Config.mongoDbConfig());

    Router router = Router.router(vertx);

    router.route(HttpMethod.GET, "/").handler(handler -> {
      logger.info("/ invoked");
      mongoClient.createCollection("keys", mongoHandler -> {
        if (mongoHandler.succeeded()) {
          System.out.println("Good");
        } else {
          System.out.println(mongoHandler.cause());
        }
      });
      HttpServerResponse response = handler.response();
      response.end("Hello World");
    });

    router.route(HttpMethod.POST, "/authent/face").handler(handler -> {
      logger.info("/auth/face invoked");
      logger.info(handler);
    });

    vertx.createHttpServer().requestHandler(router::accept).
      listen(8080, http ->
      {
        if (http.succeeded()) {
          startFuture.complete();
          System.out.println("HTTP server started on http://localhost:8080");
        } else {
          startFuture.fail(http.cause());
        }
      });
  }
}
