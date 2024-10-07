package it.gacciai.sec04;

import it.gacciai.common.Util;
import it.gacciai.sec01.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Flux create does NOT check the downstream demand by default! It is by design!
 */
public class Lec04FluxCreateDownstreamDemand {

    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {

        produceOnDemand();

    }

    private static void produceEarly() {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        var name = Util.faker().name().firstName();
                        log.info("sink generated: {}", name);
                        fluxSink.next(name);
                    }

                    log.info("sink complete");
                    fluxSink.complete();
                })
                //.log()
                .subscribe(subscriber)
        ;

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(4);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(4);

        subscriber.getSubscription().cancel();

        subscriber.getSubscription().request(4);
    }

    private static void produceOnDemand() {
        var subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {

                    fluxSink.onRequest(count -> {
                        for (int i = 0; i < count && !fluxSink.isCancelled(); i++) {
                            var name = Util.faker().name().firstName();
                            log.info("sink generated: {}", name);
                            fluxSink.next(name);
                        }
                    });
                })
                //.log()
                .subscribe(subscriber)
        ;

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(4);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(4);

        subscriber.getSubscription().cancel();

        subscriber.getSubscription().request(4);
    }

}
