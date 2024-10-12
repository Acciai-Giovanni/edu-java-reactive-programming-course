package it.gacciai.sec03;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    To emit a message based on the given interval
 */
public class Lec09FluxInterval {


    public static void main(String[] args) {
        Flux.interval(Duration.ofMillis(500))
                .log()
                .map(i -> Util.faker().name().firstName())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(10);
    }

}
