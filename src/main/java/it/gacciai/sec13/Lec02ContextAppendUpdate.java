package it.gacciai.sec13;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/*
    Context is an immutable map. We can append additional info!
 */
public class Lec02ContextAppendUpdate {

    private static final Logger log = LoggerFactory.getLogger(Lec02ContextAppendUpdate.class);

    public static void main(String[] args) {
        append();

        update();
    }

    public static void append() {
        getWelcomeMessage()
                .contextWrite(Context.of("a", "a1").put("b", "b1").put("c", "c1"))
                .contextWrite(Context.of("user", "John"))
                .subscribe(Util.subscriber());
    }

    public static void update() {
        getWelcomeMessage()
                .contextWrite(ctx -> Context.empty())
                .contextWrite(Context.of("appended", "a2"))
                .contextWrite(Context.of("appended", "a1"))
                .contextWrite(Context.of("user", "John"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx ->{
                            log.info("{}",ctx);
                            if (ctx.hasKey("user")) {
                                return Mono.just("Welcome %s".formatted(ctx.<String>get("user")));
                            } else {
                                return Mono.error(new RuntimeException("No user found"));
                            }
                        }
                );
    }

}
