package it.gacciai.sec02;

/*
    To delay the publisher creation
 */

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class Lec10MonoDefer {

    private static final Logger log = LoggerFactory.getLogger(Lec10MonoDefer.class);

    public static void main(String[] args) {

        log.info("method main starts");

        log.info("creating a slow publisher without subscriptions...");
        createPublisher(); //Slow operation even without a subscriber

        log.info("Deferring publisher creation without subscription...");
        Mono.defer(Lec10MonoDefer::createPublisher); //Fast operation!!!

        log.info("Subscribing to the deferred publisher...");
        Mono.defer(Lec10MonoDefer::createPublisher)
                .subscribe(Util.subscriber())
        ;

        log.info("method main ends");

    }

    private static Mono<Integer> createPublisher() {
        log.info("creating publisher");
        var list = List.of(1, 2, 3);
        Util.sleepSeconds(1); //Simulating a time-consuming operation
        return Mono.fromSupplier(() -> sum(list));
    }

    //Time-consuming business logic
    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(a -> a).sum();
    }

}
