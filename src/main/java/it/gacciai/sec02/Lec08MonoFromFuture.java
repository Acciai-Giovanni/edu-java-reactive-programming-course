package it.gacciai.sec02;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/*
    If you have a CompletableFuture already, then we can convert that into a Mono
 */
public class Lec08MonoFromFuture {

    public static final Logger log = LoggerFactory.getLogger(Lec08MonoFromFuture.class);

    public static void main(String[] args) {
        log.info("method main starts");

        //NOT LAZY! This will execute without the subscriber!!!!
        Mono.fromFuture(getName());

        //LAZY! This will execute only if there is a subscriber
        Mono.fromFuture(() -> getName())
                .subscribe(Util.subscriber("subscriber2"))
        ;

        //LAZY! Also, this will execute only if there is a subscriber
        Mono.fromFuture(Lec08MonoFromFuture::getName)
                .subscribe(Util.subscriber("subscriber3"))
        ;

        log.info("waiting for async completion");
        Util.sleepSeconds(3);

        log.info("method main ends");
    }

     private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            Util.sleepSeconds(1);
            log.info("generating name...");
            Util.sleepSeconds(1);
            log.info("generating name done!");
            return Util.faker().name().firstName();
        });
     }

}
