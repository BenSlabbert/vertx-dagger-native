/* Licensed under Apache-2.0 2025. */
package github.benslabbert.vdnative.di;

import dagger.Component;
import github.benslabbert.vdnative.verticle.EBVerticle;
import github.benslabbert.vdnative.web.HelloHandler;
import jakarta.inject.Singleton;

@Singleton
@Component
public interface Provider {

  EBVerticle eBVerticle();

  HelloHandler helloHandler();
}
