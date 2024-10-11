package it.gacciai.sec05;

import it.gacciai.common.Util;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
    How to handle error in a reactive pipeline
    Flux.(...)
        ...
        ...
        ...
        ...
 */
@SuppressWarnings({"unused", "divzero", "overflow"})
public class Lec06ErrorHandling {


    private static final Logger log = LoggerFactory.getLogger(Lec06ErrorHandling.class);

    public static void main(String[] args) {
        /*Flux.range(1,10)
                .log("l1")
                .map(i -> i == 5 ? 5 / 0 : i)
                .log("l2")
                .onErrorContinue(ArithmeticException.class, (ex, obj) -> log.error("==> {}", obj, ex))
                .log("l3")
                .subscribe(Util.subscriber());
        */
    }

    // skip the error and continue
    private static void onErrorContinue(){
        Flux.range(1,10)
                .map(i -> i == 5 ? 5 / 0 : i)
                .onErrorContinue(ArithmeticException.class, (ex, obj) -> log.error("==> {}", obj, ex))
                .subscribe(Util.subscriber());
    }

    // when you want to return a hardcoded value / simple computation
    private static void onErrorReturn() {
        Mono.error(new ArithmeticException())
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .subscribe(Util.subscriber());
    }

    // in case of error, emit complete
    private static void onErrorComplete() {
        Mono.error(new ArithmeticException())
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    // when you have to use another publisher in case of error
    private static void onErrorResume() {
        Mono.error(new ArithmeticException())
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback1() {
        //return Mono.fromSupplier(() -> Util.faker().random().nextInt(10,100));
        return Mono.error(new NotImplementedException("Not implemented"));
    }

    private static Mono<Integer> fallback2() {
        //return Mono.fromSupplier(() -> Util.faker().random().nextInt(100,1000));
        return Mono.error(new IllegalArgumentException());
    }

}
