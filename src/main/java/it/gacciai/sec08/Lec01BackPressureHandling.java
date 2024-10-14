package it.gacciai.sec08;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.concurrent.Queues;

/*
    Reactor automatically handles the backpressure.
    System.setProperty("reactor.bufferSize.small", "16");
 */
public class Lec01BackPressureHandling {

    private static final Logger log = LoggerFactory.getLogger(Lec01BackPressureHandling.class);

    public static void main(String[] args) {

        System.setProperty("reactor.bufferSize.small", "16");

        log.info("Small buffer size: " + Queues.SMALL_BUFFER_SIZE); //see https://projectreactor.io/docs/core/release/api/reactor/util/concurrent/Queues.html

        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating {}", state);
                            sink.next(state);
                            return ++state;
                        }
                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel())
        ;

        producer
                .publishOn(Schedulers.boundedElastic())
                .map(Lec01BackPressureHandling::timeConsumingTask)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);


    }

    private static int timeConsumingTask(int i) {
        Util.sleepSeconds(1);
        return i;
    }

}