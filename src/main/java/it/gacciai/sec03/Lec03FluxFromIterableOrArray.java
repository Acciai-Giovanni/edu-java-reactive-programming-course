package it.gacciai.sec03;


import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

/*
    To create flux from iterable or array
 */
public class Lec03FluxFromIterableOrArray {

    private static final Logger log = LoggerFactory.getLogger(Lec03FluxFromIterableOrArray.class);

    public static void main(String[] args) {

        var list = List.of(1, 2, 3);
        Flux.fromIterable(list)
                //.log()
                .subscribe(Util.subscriber());

        String[] arr = {"a", "b", "c"};
        Flux.fromArray(arr)
                //.log()
                .subscribe(
                        i -> log.info("Done " + i),
                        e -> log.error("error ", e),
                        () -> log.info("Completed!"))
        ;

    }

}
