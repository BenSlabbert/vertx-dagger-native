/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EBVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(EBVerticle.class);

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
                  log.info("Received EB message: {}", body);
                });

    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) {
    log.info("Stopping EBVerticle");
    consumer.unregister(stopPromise);
  }
}
