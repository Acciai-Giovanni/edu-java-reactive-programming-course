package it.gacciai.sec02;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/*
    To delay the execution using supplier / callable
 */
public class Lec05MonoFromSupplier {

    private static final Logger log = LoggerFactory.getLogger(Lec05MonoFromSupplier.class);

    public static void main(String[] args) {

        var list = List.of(1, 2, 3, 4);

        // Do not use JUST! it computes the sum before the subscription!
        //Mono.just(sum(list))
        //        .subscribe(Util.subscriber());

        log.info("Sum is executed only if there is a subscriber");
        Mono.fromSupplier(() -> sum(list))
        //        .subscribe(Util.subscriber())
        ;

        log.info("publishing three times");
        var publisher = Mono.fromSupplier(() -> sum(list));
        publisher.subscribe(Util.subscriber("S1"));
        publisher.subscribe(Util.subscriber("S2"));
        publisher.subscribe(Util.subscriber("S3"));
        log.info("done subscribing");

    }

    /**
     * Simulating a computing intensive operation on multiple items
     */
    private static int sum(List<Integer> list) {
        log.info("finding the sum of {}", list);

        try {
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return list
                .stream()
                .peek(i -> log.info("peeking {}", i))
                .mapToInt(i -> i)
                .peek(i -> log.info("peeking int {}", i))
                .sum();
    }
}
