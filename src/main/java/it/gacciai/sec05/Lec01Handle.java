package it.gacciai.sec05;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Handle behaves like filter + map

    1 => -2
    4 => do not send
    7 => error
    everything else => send as it is
*/
public class Lec01Handle {

    private static final Logger log = LoggerFactory.getLogger(Lec01Handle.class);

    public static void main(String[] args) {

        Flux<Integer> flux = Flux.range(1, 10);

        Flux<Integer> handledFlux = flux
                .filter(i -> i != 7)
                .handle((item, sink) -> {
                    switch (item) {
                        case 1:
                            sink.next(-2);
                            break;
                        case 4: log.warn("skipping item: {}", item);
                            break;
                        case 7:
                            sink.error(new RuntimeException("error"));
                            break;
                        default:
                            sink.next(item);
                    }
                })
                .cast(Integer.class)
                ;

        //flux.subscribe(Util.subscriber()); // 1->10

        handledFlux
                .subscribe(Util.subscriber()); // 1->6
    }
}
