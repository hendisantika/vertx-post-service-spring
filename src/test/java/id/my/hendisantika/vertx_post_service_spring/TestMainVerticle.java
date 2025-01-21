package id.my.hendisantika.vertx_post_service_spring;

import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.logging.Logger;

@SpringJUnitConfig(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(VertxExtension.class)
public class TestMainVerticle {
  private final static Logger LOGGER = Logger.getLogger(TestMainVerticle.class.getName());

  @Autowired
  ApplicationContext context;

  Vertx vertx;

  @BeforeAll
  public void setupAll(VertxTestContext testContext) {
    vertx = context.getBean(Vertx.class);
    var factory = context.getBean(VerticleFactory.class);
    vertx.deployVerticle(factory.prefix() + ":" + MainVerticle.class.getName())
      .onSuccess(id -> {
        LOGGER.info("deployed:" + id);
        testContext.completeNow();
      })
      .onFailure(testContext::failNow);
  }

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle()).onComplete(testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }
}
