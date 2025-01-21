package id.my.hendisantika.vertx_post_service_spring.exception;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : vertx-post-service-spring
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 21/01/25
 * Time: 08.20
 * To change this template use File | Settings | File Templates.
 */
public class PostNotFoundException extends RuntimeException {
  public PostNotFoundException(UUID id) {
    super("Post id: " + id + " was not found. ");
  }
}
