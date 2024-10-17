package it.gacciai.sec09;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {

    record Car(String body, String engine, String tires){}

    public static void main(String[] args) {

        Flux.zip(getBody(), getEngine(), getTires())
                .map(t -> new Car(t.getT1(), t.getT2(), t.getT3()))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Flux<String> getBody(){
        return Flux.range(1, 5)
                .map(i -> "body-" + i)
                .log("body")
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine(){
        return Flux.range(1, 3)
                .map(i -> "engine-" + i)
                .log("engine")
                .delayElements(Duration.ofMillis(800));
    }

    private static Flux<String> getTires(){
        return Flux.range(1, 10)
                .map(i -> "tires-" + i)
                .log("tires")
                .delayElements(Duration.ofMillis(75));
    }


}
