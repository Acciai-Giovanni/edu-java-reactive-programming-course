package it.gacciai.sec02;

/*
    Emitting empty / error
 */

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {

    private static final Logger log = LoggerFactory.getLogger(Lec04MonoEmptyError.class);

    public static void main(String[] args) {

            log.info("Subscribing without handling error - We will get an ugly stack trace");
            getUsername(3)
                    .subscribe(Util.subscriber());

            log.info("Subscribing and explicitly ignoring the error - We will log nothing");
            getUsername(4)
                    .subscribe(
                            item -> log.info("item is: {}", item),
                            err-> {}

                    );
    }

    private static Mono<String> getUsername(int userId){
        return switch (userId){
            case 1 -> Mono.just("john.doe");
            case 2 -> Mono.empty(); // null
            default -> Mono.error(new RuntimeException("invalid input"));
        };
    }

}
