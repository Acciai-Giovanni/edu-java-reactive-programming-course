package it.gacciai.sec04;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class Lec08GenerateWithState {

    public static void main(String[] args) {

        Flux.<String,Integer>generate(
                        () -> 0,
                        (counter, sink) -> {
                            var country = Util.faker().country().name();
                            sink.next(country);

                            if (counter + 1 == 10 || country.equalsIgnoreCase("canada")) {
                                sink.complete();
                            }

                            return counter + 1;
                        },
                        counter -> System.out.println("counter: " + counter)

                )
                .subscribe(Util.subscriber());

    }

    private static void implementation1() {
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
