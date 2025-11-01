/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.internal.logging.Logger;
import io.vertx.core.internal.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;

public class EBVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(EBVerticle.class.getName());

  private MessageConsumer<Object> consumer;

  @Inject
  EBVerticle() {}

  @Override
  public void start(Promise<Void> startPromise) {
    log.info("Starting EBVerticle");
    consumer =
        vertx
            .eventBus()
            .consumer("address")
            .handler(
                message -> {
                  JsonObject body = (JsonObject) message.body();
                  log.info("Received EB message: %s".formatted(body));
                });

    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping EBVerticle");
    consumer.unregister().onComplete(stopPromise);
  }
}
