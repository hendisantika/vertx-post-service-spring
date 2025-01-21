package id.my.hendisantika.vertx_post_service_spring.repository;

import id.my.hendisantika.vertx_post_service_spring.model.Post;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.logging.Logger;

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
@Component
@RequiredArgsConstructor
public class PostRepository {
  private static final Logger LOGGER = Logger.getLogger(PostRepository.class.getName());

  private static final Function<Row, Post> MAPPER = (row) ->
    Post.of(
      row.getUUID("id"),
      row.getString("title"),
      row.getString("content"),
      row.getLocalDateTime("created_at")
    );


  private final Pool client;
}
