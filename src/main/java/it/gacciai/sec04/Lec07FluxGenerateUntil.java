package it.gacciai.sec04;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

public class Lec07FluxGenerateUntil {

    public static void main(String[] args) {
        Flux.<String>generate(synchronousSync -> {
                    synchronousSync.next(Util.faker().country().name());
                })
                .takeUntil("canada"::equalsIgnoreCase)
                .subscribe(Util.subscriber());
    }
}
