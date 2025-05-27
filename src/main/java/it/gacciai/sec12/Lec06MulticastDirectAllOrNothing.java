package it.gacciai.sec12;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

import java.time.Duration;

public class Lec06MulticastDirectAllOrNothing {

    private static final Logger log = LoggerFactory.getLogger(Lec06MulticastDirectAllOrNothing.class);

    public static void main(String[] args) {

        demo1();

        Util.sleepSeconds(10);

    }

    /*
        directAllOrNothing - all or nothing - either deliver to all the subscribers or no one!
    */
    private static void demo1(){

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directAllOrNothing();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("quick sam"));
        flux.delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("slow mike"));

        for (int i = 1; i <= 100 ; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

    }

    /**
     * directAllOrNothing si comporta in maniera inattesa se un sottoscrittore lento ha una queue dedicata!
     */
    private static void demo2(){

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directAllOrNothing();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("quick sam"));
        flux.onBackpressureBuffer(20).delayElements(Duration.ofMillis(200)).subscribe(Util.subscriber("slow mike"));

        //Il directAllOrNothing NON SA che i messaggi per slow mike sono andati persi a causa di un buffer overflow!

        for (int i = 1; i <= 100 ; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

    }



}
