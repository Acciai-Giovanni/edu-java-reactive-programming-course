package it.gacciai.sec04;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {

    public static void main(String[] args) {

        AtomicInteger counter = new AtomicInteger();
        Flux.generate(synchronousSink -> {
                    var country = Util.faker().country().name();
                    synchronousSink.next(country);

                    int c = counter.incrementAndGet();
                    if (c == 10 || country.equalsIgnoreCase("canada")) {
                        synchronousSink.complete();
                    }
                })
                // .take(50)
                .subscribe(Util.subscriber());

    }
}
