package it.gacciai.sec05;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Similar to error handling.
    Handling empty!
 */
@SuppressWarnings({"unused"})
public class Lec08SwitchIfEmpty {

    private static final Logger log = LoggerFactory.getLogger(Lec08SwitchIfEmpty.class);

    public static void main(String[] args) {

        /*
            Redis Cache with fallback on DB
         */

        Flux.range(1, 10)
                .filter(i -> i > 10)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());


    }

    private static Flux<Integer> fallback(){
        return Flux.range(100, 3);
    }

}
