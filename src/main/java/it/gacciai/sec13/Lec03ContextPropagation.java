package it.gacciai.sec13;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class Lec03ContextPropagation {

    private static final Logger log = LoggerFactory.getLogger(Lec03ContextPropagation.class);

    public static void main(String[] args) {
        getWelcomeMessage()
                .concatWith(Flux.merge(
                        producer1(),
                        producer2().contextWrite(ctx -> Context.empty())
                ))
                .contextWrite(Context.of("user", "Giovanni"))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(1); //Simulating a time-consuming operation
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("{}",ctx);
            if (ctx.hasKey("user")) {
                return Mono.just("Welcome %s".formatted(ctx.<String>get("user")));
            } else {
                return Mono.error(new RuntimeException("No user found"));
            }
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
                    log.info("producer1: {}", ctx);
                    return Mono.empty();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
                    log.info("producer2: {}", ctx);
                    return Mono.empty();
                })
                .subscribeOn(Schedulers.parallel());
    }

}
