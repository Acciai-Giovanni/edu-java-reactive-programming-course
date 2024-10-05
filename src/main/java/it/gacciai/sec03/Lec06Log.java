package it.gacciai.sec03;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

public class Lec06Log {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .log("lec06log.range.map")
                //.filter(Lec06Log::isEven)
                .map(i -> Util.faker().name().fullName())
                .log("lec06log.map.subscriber")
                .subscribe(Util.subscriber());
    }

    private static boolean isEven(int i) {
        return i % 2 == 0;
    }

}
