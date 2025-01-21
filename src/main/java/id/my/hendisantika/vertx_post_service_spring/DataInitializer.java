package id.my.hendisantika.vertx_post_service_spring;

import io.vertx.sqlclient.Pool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-post-service-spring
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/01/25
 * Time: 08.18
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

  private final static Logger LOGGER = Logger.getLogger(DataInitializer.class.getName());

  private final Pool client;
}
