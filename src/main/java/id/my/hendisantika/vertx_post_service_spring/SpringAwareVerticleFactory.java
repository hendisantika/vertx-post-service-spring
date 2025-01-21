package id.my.hendisantika.vertx_post_service_spring;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-post-service-spring
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/01/25
 * Time: 08.25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SpringAwareVerticleFactory implements VerticleFactory, ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public String prefix() {
    return "spring";
  }

  @Override
  public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
    String clazz = VerticleFactory.removePrefix(verticleName);
    promise.complete(() -> (Verticle) applicationContext.getBean(Class.forName(clazz)));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
