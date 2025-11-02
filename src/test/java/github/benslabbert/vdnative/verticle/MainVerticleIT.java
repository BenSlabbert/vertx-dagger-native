/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.verticle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class MainVerticleIT {

  private MainVerticle verticle;

  @BeforeEach
  void deploy(Vertx vertx, VertxTestContext tc) {
    verticle = new MainVerticle();
    vertx
        .deployVerticle(
            verticle,
            new DeploymentOptions().setConfig(new JsonObject().put("http.port", 0)).setInstances(1))
        .onComplete(tc.succeedingThenComplete());
  }

  @Test
  void some_test(Vertx vertx, VertxTestContext tc) {
    vertx
        .createHttpClient()
        .request(HttpMethod.GET, verticle.getPort(), "127.0.0.1", "/hello")
        .onComplete(
            ar -> {
              if (ar.failed()) {
                tc.failNow(ar.cause());
                return;
              }

              ar.result()
                  .send()
                  .onComplete(
                      tc.succeeding(
                          resp ->
                              tc.verify(
                                  () -> {
                                    assertEquals(200, resp.statusCode());
                                    tc.completeNow();
                                  })));
            });
  }
}
