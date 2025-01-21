package id.my.hendisantika.vertx_post_service_spring.handler;

import id.my.hendisantika.vertx_post_service_spring.CreatePostCommand;
import id.my.hendisantika.vertx_post_service_spring.model.Post;
import id.my.hendisantika.vertx_post_service_spring.repository.PostRepository;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Level;
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

  public void get(RoutingContext rc) {
    var params = rc.pathParams();
    var id = params.get("id");
    this.posts.findById(UUID.fromString(id))
      .onSuccess(
        post -> rc.response().end(Json.encode(post))
      )
      .onFailure(
        throwable -> rc.fail(404, throwable)
      );
  }

  public void save(RoutingContext rc) {
    //rc.getBodyAsJson().mapTo(PostForm.class)
    var body = rc.body().asJsonObject();
    LOGGER.log(Level.INFO, "request body: {0}", body);
    var form = body.mapTo(CreatePostCommand.class);
    this.posts
      .save(Post.builder()
        .title(form.title())
        .content(form.content())
        .build()
      )
      .onSuccess(
        savedId -> rc.response()
          .putHeader("Location", "/posts/" + savedId)
          .setStatusCode(201)
          .end()

      );
  }

  public void update(RoutingContext rc) {
    var params = rc.pathParams();
    var id = params.get("id");
    var body = rc.body().asJsonObject();
    LOGGER.log(Level.INFO, "\npath param id: {0}\nrequest body: {1}", new Object[]{id, body});
    var form = body.mapTo(CreatePostCommand.class);
    this.posts.findById(UUID.fromString(id))
      .compose(
        post -> {
          post.setTitle(form.title());
          post.setContent(form.content());

          return this.posts.update(post);
        }
      )
      .onSuccess(
        data -> rc.response().setStatusCode(204).end()
      )
      .onFailure(
        throwable -> rc.fail(404, throwable)
      );
  }

}
