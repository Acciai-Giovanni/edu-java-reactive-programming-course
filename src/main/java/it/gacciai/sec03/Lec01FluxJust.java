package it.gacciai.sec03;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

/*
    To create a Flux with arbitrary items in memory
 */
public class Lec01FluxJust {

    public static void main(String[] args) {

        Flux.just(1, 2, 3, 4, "a")
                //.log()
                .subscribe(Util.subscriber())
        ;
    }

}
