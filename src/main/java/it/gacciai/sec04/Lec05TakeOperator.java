package it.gacciai.sec04;

import reactor.core.publisher.Flux;

import java.util.stream.IntStream;

/*
    Take is similar to java stream's limit
 */
public class Lec05TakeOperator {

    public static void main(String[] args) {

        streamRangeClosed();

        System.out.println("====================================");

        take();

        System.out.println("====================================");

        takeWhile();

        System.out.println("====================================");

        takeUntil();
    }

    private static void streamRangeClosed() {
        IntStream.rangeClosed(1, 10)
                .limit(3)
                .forEach(System.out::println);
    }

    private static void take() {
        Flux.range(1, 10)
                .log("take") // will get request(3)
                .take(3) // discarding request(unbounded) and requesting 3
                .log("inbetween")
                .take(1) // Request 1 and then CANCEL the chain up and COMPLETE the chain down
                .log("subscribe") // will get request(unbounded)
                .subscribe(System.out::println);
    }

    private static void takeWhile() {
        Flux.range(1, 10)
                .log("take")
                .takeWhile(i -> i < 5) // stop when the condition is not met + DO NOT ALLOW the last item to pass
                .log("sbsr")
                .subscribe(System.out::println);
    }

    private static void takeUntil() {
        Flux.range(1, 10)
                .log("take")
                .takeUntil(i -> i >= 5) // stop after the condition is met + allow the last item to pass
                .log("sbsr")
                .subscribe(System.out::println);
    }

}
