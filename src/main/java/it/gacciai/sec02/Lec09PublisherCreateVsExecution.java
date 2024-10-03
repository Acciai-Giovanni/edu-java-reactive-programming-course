package it.gacciai.sec02;


/*
    Creating publisher is a lightweight operation.
    Executing time-consuming business logic can be delayed
 */

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec09PublisherCreateVsExecution {

    private static final Logger log = LoggerFactory.getLogger(Lec09PublisherCreateVsExecution.class);

    public static void main(String[] args) {
        log.info("method main starts");

        getName()
                .subscribe(Util.subscriber())
        ;

        log.info("method main ends");
    }

    private static Mono<String> getName(){
        log.info("entered the method");
        return Mono.fromSupplier(() -> {
            log.info("generating name");
            Util.sleepSeconds(3);
            return Util.faker().name().firstName();
        });
    }

}
