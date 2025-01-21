package id.my.hendisantika.vertx_post_service_spring;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import id.my.hendisantika.vertx_post_service_spring.handler.PostsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.jackson.DatabindCodec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MainVerticle extends AbstractVerticle {
  private final static Logger LOGGER = Logger.getLogger(MainVerticle.class.getName());

  static {
    LOGGER.info("Customizing the built-in jackson ObjectMapper...");
    var objectMapper = DatabindCodec.mapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    objectMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);

    JavaTimeModule module = new JavaTimeModule();
    objectMapper.registerModule(module);
  }

  final PostsHandler postHandlers;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
