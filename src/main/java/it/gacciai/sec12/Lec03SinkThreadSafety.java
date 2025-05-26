package it.gacciai.sec12;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Lec03SinkThreadSafety {

    private static final Logger log = LoggerFactory.getLogger(Lec03SinkThreadSafety.class);

    public static void main(String[] args) {

        demo1b();

    }

    private static void demo1(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        // arraylist is not thread safe.
        // intentionally chosen for demo purposes.
        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.tryEmitNext(j);
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }

    private static void demo1b(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();


        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                var result = sink.tryEmitNext(j);
                if (!result.isSuccess()) {
                    log.info("failed to emit signal {}: {}", j, result);
                }
            });
        }

        flux.subscribe(Util.subscriber("flux-subscriber"));

        Util.sleepSeconds(2);

    }

    private static void demo2(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        // arraylist is not thread safe.
        // intentionally chosen for demo purposes.
        var list = new ArrayList<>();
        flux.subscribe(list::add);

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                sink.emitNext(j, (signal, emitResult) -> {
                    log.info("emitting signal {}", emitResult);
                    return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                });
            });
        }

        Util.sleepSeconds(2);

        log.info("list size: {}", list.size());
    }


    private static void demo3(){
        var sink = Sinks.many().unicast().onBackpressureBuffer();
        var flux = sink.asFlux();

        // arraylist is not thread safe.
        // intentionally chosen for demo purposes.
        var list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            var j = i;
            CompletableFuture.runAsync(() -> {
                var result = sink.tryEmitNext(j);
                if (!result.isSuccess()) {
                    log.info("failed to emit signal {}: {}", j, result);
                }
            });
        }

        Util.sleepSeconds(10);

        //flux.log().subscribe(list::add);
        flux.log().subscribe(Util.subscriber());

        log.info("list size: {}", list.size());
    }

}

