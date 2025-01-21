package id.my.hendisantika.vertx_post_service_spring;

import io.vertx.core.spi.VerticleFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
}
