package id.my.hendisantika.vertx_post_service_spring;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

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

  private final Promise<Void> ready = Promise.promise();

  /**
   * Completes once the sample data has been (re)initialized, or fails if initialization failed.
   */
  public Future<Void> ready() {
    return ready.future();
  }

  @EventListener(ContextRefreshedEvent.class)
  public void run() {
    LOGGER.info("Data initialization is starting...");

    Tuple first = Tuple.of("Hello Quarkus", "My first post of Quarkus");
    Tuple second = Tuple.of("Hello Again, Quarkus", "My second post of Quarkus");

    client
      .withTransaction(
        conn -> conn.query("DELETE FROM posts").execute()
          .flatMap(r -> conn.preparedQuery("INSERT INTO posts (title, content) VALUES ($1, $2)")
            .executeBatch(List.of(first, second))
          )
          .flatMap(r -> conn.query("SELECT * FROM posts").execute())
      )
      .onSuccess(data -> StreamSupport.stream(data.spliterator(), true)
        .forEach(row -> LOGGER.log(Level.INFO, "saved data:{0}", new Object[]{row.toJson()}))
      )
      .onComplete(
        r -> {
          //client.close(); will block the application.
          LOGGER.info("Data initialization is done...");
          if (r.succeeded()) {
            ready.tryComplete();
          } else {
            ready.tryFail(r.cause());
          }
        }
      )
      .onFailure(
        throwable -> LOGGER.warning("Data initialization is failed:" + throwable.getMessage())
      );
  }
}
