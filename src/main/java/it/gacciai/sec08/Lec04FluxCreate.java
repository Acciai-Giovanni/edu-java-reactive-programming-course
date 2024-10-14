package it.gacciai.sec08;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * This is an example on how NOT to use Flux.create,
 * it does not manage backpressure automatically.
 *
 * Only a couple of items are emitted immediately to the consumer, it will start to work only after sink.complete() is called.
 */
public class Lec04FluxCreate {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreate.class);

    public static void main(String[] args) {

        //The Subscriber will receive only 16 items, then it will get the remaining 484 only after sink.complete!!!!
        System.setProperty("reactor.bufferSize.small", "16");

        var producer = Flux.create(sink -> {
                    for (int i = 1; i <= 500 && !sink.isCancelled(); i++) {
                        log.info("generating {}", i);
                        sink.next(i);
                        Util.sleep(Duration.ofMillis(50));
                    }
                    sink.complete();
                })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .limitRate(1) //this WORSEN the problem, The subscriber will receive only 1 item!!!
                .publishOn(Schedulers.boundedElastic())
                .map(Lec04FluxCreate::timeConsumingTask)
                .subscribe();

        Util.sleepSeconds(60);


    }

    private static int timeConsumingTask(int i) {
        log.info("received: {}", i);
        Util.sleepSeconds(1);
        return i;
    }

}
