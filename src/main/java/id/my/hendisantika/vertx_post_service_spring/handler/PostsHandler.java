package id.my.hendisantika.vertx_post_service_spring.handler;

import id.my.hendisantika.vertx_post_service_spring.repository.PostRepository;
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
 * Time: 08.23
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
class PostsHandler {
  private static final Logger LOGGER = Logger.getLogger(PostsHandler.class.getSimpleName());

  private final PostRepository posts;
}
