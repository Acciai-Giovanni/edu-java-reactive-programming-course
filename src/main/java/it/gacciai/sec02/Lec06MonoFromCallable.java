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
public class Lec06MonoFromCallable {

    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

    public static void main(String[] args) {

        var list = List.of(1, 2, 3);

        log.info("Subscription will evoke \"onError\" on the subscriber! ");
        var publisher = Mono.fromCallable(() -> sum(list));
        publisher.subscribe(Util.subscriber("S1"));
        log.info("done subscribing");

    }

    /**
     * Simulating a computing intensive operation on multiple items
     */
    private static int sum(List<Integer> list) throws Exception {
        log.info("finding the sum of {}", list);

        Thread.sleep(Duration.ofSeconds(1));

        int sum = list
                .stream()
                .peek(i -> log.info("peeking {}", i))
                .mapToInt(i -> i)
                .peek(i -> log.info("peeking int {}", i))
                .sum();

        if (sum > 9) {
            throw new Exception("Sum is greater than 9");
        }

        return sum;
    }
}
