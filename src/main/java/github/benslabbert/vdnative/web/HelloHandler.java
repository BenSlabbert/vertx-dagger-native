/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.web;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HelloHandler implements Handler<RoutingContext> {

  private static final Logger log = LoggerFactory.getLogger(HelloHandler.class);

  @Inject
  HelloHandler() {}

  @Override
  public void handle(RoutingContext ctx) {
    log.info("Handling request");
    ctx.put(CONTENT_TYPE.toString(), TEXT_PLAIN.toString()).end(Buffer.buffer("hello"));
  }
}
