/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class HelloHandlerTest {

  @Test
  void example() {
    HelloHandler helloHandler = new HelloHandler();
    RoutingContext ctx = Mockito.mock(RoutingContext.class);
    when(ctx.put(anyString(), anyString())).thenReturn(ctx);

    helloHandler.handle(ctx);

    verify(ctx).put(anyString(), anyString());
    ArgumentCaptor<Buffer> captor = ArgumentCaptor.forClass(Buffer.class);
    verify(ctx).end(captor.capture());

    assertEquals("hello", captor.getValue().toString());
  }
}
