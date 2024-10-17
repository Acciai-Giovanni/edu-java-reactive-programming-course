package it.gacciai.sec09;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    To collect the items received via Flux. Assuming we will have finite items!
 */
public class Lec14CollectList {

    public static void main(String[] args) {

        Flux.range(1, 10)
                .concatWith(Mono.error(new RuntimeException("Error")))
            .collectList()
            .subscribe(Util.subscriber());

    }

}
