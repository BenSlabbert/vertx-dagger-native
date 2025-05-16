/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.verticle;

import static io.vertx.core.ThreadingModel.EVENT_LOOP;
import static io.vertx.core.ThreadingModel.VIRTUAL_THREAD;
import static io.vertx.core.ThreadingModel.WORKER;

import github.benslabbert.vdnative.di.DaggerProvider;
import github.benslabbert.vdnative.di.Provider;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(MainVerticle.class);

  private HttpServer server;
  long timerId = -1L;

  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting vdnative");

    Provider provider = DaggerProvider.create();

    // each instance of this verticle will deploy two of these
    vertx.deployVerticle(
        provider::eBVerticle,
        // VIRTUAL_THREAD does not work on native image
        new DeploymentOptions().setThreadingModel(VIRTUAL_THREAD).setInstances(2));
    vertx.deployVerticle(
        provider::eBVerticle, new DeploymentOptions().setThreadingModel(WORKER).setInstances(2));
    vertx.deployVerticle(
        provider::eBVerticle,
        new DeploymentOptions().setThreadingModel(EVENT_LOOP).setInstances(2));

    timerId =
        vertx.setPeriodic(
            100L,
            500L,
            id -> vertx.eventBus().publish("address", new JsonObject().put("key", "value-" + id)));

    Router router = Router.router(vertx);
    router
        .route("/")
        .handler(
            ctx -> {
              log.info("Handling request");
              ctx.end(Buffer.buffer("hello"));
            });

    Integer port = config().getInteger("http.port", 8080);

    vertx
        .createHttpServer(new HttpServerOptions().setPort(port))
        .requestHandler(router)
        .listen()
        .onComplete(
            s -> {
              if (s.failed()) {
                startPromise.fail(s.cause());
                return;
              }
              server = s.result();
              startPromise.complete();
            });
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping vdnative");

    boolean cancelled = vertx.cancelTimer(timerId);
    log.info("timer {} cancelled ? {}", timerId, cancelled);

    server
        .close()
        .onComplete(
            ar -> {
              if (ar.failed()) {
                stopPromise.fail(ar.cause());
                return;
              }

              stopPromise.complete();
            });
  }
}
