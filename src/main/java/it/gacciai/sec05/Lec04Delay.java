package it.gacciai.sec05;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04Delay {

    private static final Logger log = LoggerFactory.getLogger(Lec04Delay.class);

    public static void main(String[] args) {

        log.info("start");

        Flux.range(1, 10)
                .log() // the delay request one item each time
                .delayElements(Duration.ofMillis(0)) // From now on it runs on a different thread!
                .subscribe(Util.subscriber());

        log.info("waiting to complete...");
        Util.sleepSeconds(1);

    }

}
