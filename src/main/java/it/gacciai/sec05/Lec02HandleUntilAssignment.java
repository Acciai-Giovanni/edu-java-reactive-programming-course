package it.gacciai.sec05;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

public class Lec02HandleUntilAssignment {

    public static void main(String[] args) {

        Flux.<String>generate(sink -> {
                    sink.next(Util.faker().country().name());
                })
                .<String>handle((country, sink) -> {
                    sink.next(country);
                    if ("canada".equalsIgnoreCase(country)) {
                        sink.complete();
                    }
                })
                .subscribe(Util.subscriber());

    }

}
