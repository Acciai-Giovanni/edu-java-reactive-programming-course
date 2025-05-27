package it.gacciai.sec12;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class Lec05MulticastDirectBestEffort {

    private static final Logger log = LoggerFactory.getLogger(Lec05MulticastDirectBestEffort.class);

    public static void main(String[] args) {

        demo3();

        Util.sleepSeconds(10);
    }

    /*
        When we have multiple subscribers, if one subscriber is slow,
        we might not be able to safely deliver messages to all the subscribers /
        other fast subscribers might not get the messages.
     */
    private static void demo1(){

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().onBackpressureBuffer();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribeOn(Schedulers.parallel()).subscribe(Util.subscriber("quick sam"));
        flux.subscribeOn(Schedulers.parallel()).delayElements(Duration.ofMillis(0)).subscribe(Util.subscriber("slow mike"));

        for (int i = 1; i <= 100 ; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

    }

    /*
        directBestEffort - focus on the fast subscriber and ignore the slow subscriber
     */
    private static void demo2(){

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directBestEffort();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux.subscribeOn(Schedulers.parallel()) .subscribe(Util.subscriber("quick sam"));
        flux.subscribeOn(Schedulers.parallel()).delayElements(Duration.ofMillis(0)).subscribe(Util.subscriber("slow mike"));

        for (int i = 1; i <= 100 ; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

    }

    /*
    give priority to the fast subscriber with .directBestEffort()
    and put the slow subscriber on a dedicated 256 BOUNDED queue (onBackpressureBuffer)
     */
    private static void demo3(){

        System.setProperty("reactor.bufferSize.small", "16");

        // handle through which we would push items
        // onBackPressureBuffer - bounded queue
        var sink = Sinks.many().multicast().directBestEffort();

        // handle through which subscribers will receive items
        var flux = sink.asFlux();

        flux
                .subscribe(Util.subscriber("quick sam"));

        flux
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.parallel()).delayElements(Duration.ofMillis(20)).subscribe(Util.subscriber("slow mike"));

        for (int i = 1; i <= 100 ; i++) {
            var result = sink.tryEmitNext(i);
            log.info("item: {}, result: {}", i, result);
        }

    }

}

