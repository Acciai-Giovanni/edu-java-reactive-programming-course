package it.gacciai.sec06;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec01ColdPublisher {

    private static final Logger log = LoggerFactory.getLogger(Lec01ColdPublisher.class);

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        var flux = Flux.create(fluxSink -> {
                    log.info("invoked");
                    for (int i = 0; i < 3; i++) {
                        fluxSink.next(atomicInteger.incrementAndGet());
                    }

                    fluxSink.complete();
                });

        flux
                //.delayElements(Duration.ofMillis(0))
                .subscribe(Util.subscriber("S1"));
        flux
                //.delayElements(Duration.ofMillis(0))
                .subscribe(Util.subscriber("S2"));

        Util.sleepSeconds(2);
    }
}
