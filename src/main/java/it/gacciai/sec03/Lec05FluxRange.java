package it.gacciai.sec03;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    To create a range of items based on the given start and count
 */
public class Lec05FluxRange {

    private static final Logger log = LoggerFactory.getLogger(Lec05FluxRange.class);

    public static void main(String[] args) {

        Flux.range(1, 10)
                .subscribe(Util.subscriber());

        //Assignment generate 10 random fake names

        Flux.range(1, 10)
                .map(i -> i + " " + Util.faker().name().firstName())
                .subscribe(Util.subscriber());

        Flux.range(1, 10)
                .map(i -> i + " " + Util.faker().hobbit().quote())
                .subscribe(Util.subscriber());

    }

}
