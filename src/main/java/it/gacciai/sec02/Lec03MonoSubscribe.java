package it.gacciai.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Lec03MonoSubscribe  {

    public static final Logger log = LoggerFactory.getLogger(Lec02MonoJust.class);

    public static void main(String[] args) {

        var mono = Mono.just(1)
                //.map(i -> i / 0)
                ;

        mono.subscribe(
                next -> log.info("Received: {}", next),
                throwable -> log.error("Error", throwable),
                () -> log.info("Completed"),
                subscription -> subscription.request(Long.MAX_VALUE)
        );
    }

}
