package id.my.hendisantika.vertx_post_service_spring.handler;

import id.my.hendisantika.vertx_post_service_spring.repository.PostRepository;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
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

  public void all(RoutingContext rc) {
//        var params = rc.queryParams();
//        var q = params.get("q");
//        var limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("q"));
//        var offset = params.get("offset") == null ? 0 : Integer.parseInt(params.get("offset"));
//        LOGGER.log(Level.INFO, " find by keyword: q={0}, limit={1}, offset={2}", new Object[]{q, limit, offset});
    this.posts.findAll()
      .onSuccess(
        data -> rc.response().end(Json.encode(data))
      );
  }
}
