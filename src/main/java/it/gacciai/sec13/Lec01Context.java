package it.gacciai.sec13;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

/*
    Context is for providing metadata about the request (similar to http headers)
 */
public class Lec01Context {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec01Context.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                .contextWrite(ctx -> ctx.put("user", "Giovanni"))
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
                )
                .contextWrite(ctx -> ctx.put("user", "John"))
                ;
    }
}
