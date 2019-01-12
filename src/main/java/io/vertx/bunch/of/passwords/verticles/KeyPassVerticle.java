package io.vertx.bunch.of.passwords.verticles;


import io.vertx.bunch.of.passwords.config.Config;
import io.vertx.bunch.of.passwords.domain.Key;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.ParsedHeaderValues;
import io.vertx.ext.web.Router;

public class KeyPassVerticle extends AbstractVerticle {

  Logger logger = LoggerFactory.getLogger(KeyPassVerticle.class);

  @Override
  public void start(Future<Void> startFuture) {

    MongoClient mongoClient = MongoClient.createShared(vertx, Config.mongoDbConfig());

    Router router = Router.router(vertx);

    router.route(HttpMethod.GET, "/").handler(handler -> {
      logger.info("/ invoked");
      HttpServerResponse response = handler.response();
      response.end("Hello World");
    });

    router.post("/keys").handler(keyHandler -> {

      Key key = new Key();
      HttpServerRequest request = keyHandler.request();
      key.setLogin(request.getHeader("login"));
      key.setPassword(request.getHeader("password"));
      key.setLink(request.getHeader("link"));
      System.out.println(request.getHeader("link"));

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
