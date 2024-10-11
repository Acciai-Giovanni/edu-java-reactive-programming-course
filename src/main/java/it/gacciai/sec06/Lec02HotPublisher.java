package it.gacciai.sec06;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Hot - 1 data producer for all the subscribers.
    share => publish().refCount(1)
    It needs 1 min subscriber to emit data.
    It stops when there is 0 subscriber.
    Re-subscription - It starts again where there is a new subscriber.
    To have min 2 subscribers, use publish().refCount(2);
 */
public class Lec02HotPublisher {

    private static final Logger log = LoggerFactory.getLogger(Lec02HotPublisher.class);

    public static void main(String[] args) {

        var movieFlux = movieStream()
                //.publish().refCount(2)
                .share(); // As long as there is a subscribers, all the subscribers will get the same data

        Util.sleepSeconds(2);

        movieFlux
                .take(5)
                .subscribe(Util.subscriber("sam"));

        Util.sleepSeconds(2);

        movieFlux
                .take(4)
                .subscribe(Util.subscriber("john")); // Late subscriber will miss the first 2 scenes

        Util.sleepSeconds(6);

        movieFlux
                .take(1)
                .subscribe(Util.subscriber("mary"));

        Util.sleepSeconds(6);
    }

    //Movie Theater
    private static Flux<String> movieStream() {
        return Flux.generate(
                () -> {
                    log.info("received the request");
                    return 1;
                },
                (state, sink) -> {
                    var scene = "movie scene " + state;
                    log.info("playing {}", scene);
                    sink.next(scene);
                    return ++state;
                }
        )
                .take(4)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class)
                ;
    }
}
