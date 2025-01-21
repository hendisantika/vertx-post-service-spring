package id.my.hendisantika.vertx_post_service_spring.repository;

import id.my.hendisantika.vertx_post_service_spring.exception.PostNotFoundException;
import id.my.hendisantika.vertx_post_service_spring.model.Post;
import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

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

  public Future<List<Post>> findAll() {
    return client.query("SELECT * FROM posts ORDER BY id ASC")
      .execute()
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .toList()
      );
  }

  public Future<Post> findById(UUID id) {
    Objects.requireNonNull(id, "id can not be null");
    return client.preparedQuery("SELECT * FROM posts WHERE id=$1").execute(Tuple.of(id))
      .map(RowSet::iterator)
      .map(iterator -> {
        if (iterator.hasNext()) {
          return MAPPER.apply(iterator.next());
        }
        throw new PostNotFoundException(id);
      });
  }

  public Future<UUID> save(Post data) {
    return client.preparedQuery("INSERT INTO posts(title, content) VALUES ($1, $2) RETURNING (id)")
      .execute(Tuple.of(data.getTitle(), data.getContent()))
      .map(rs -> rs.iterator().next().getUUID("id"));
  }

  public Future<Integer> saveAll(List<Post> data) {
    var tuples = data.stream()
      .map(
        d -> Tuple.of(d.getTitle(), d.getContent())
      )
      .toList();

    return client.preparedQuery("INSERT INTO posts (title, content) VALUES ($1, $2)")
      .executeBatch(tuples)
      .map(SqlResult::rowCount);
  }

  public Future<Integer> update(Post data) {
    return client.preparedQuery("UPDATE posts SET title=$1, content=$2 WHERE id=$3")
      .execute(Tuple.of(data.getTitle(), data.getContent(), data.getId()))
      .map(SqlResult::rowCount);
  }
}
