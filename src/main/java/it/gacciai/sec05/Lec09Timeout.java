package it.gacciai.sec05;


import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
    timeout - will produce timeout error.
        - We can handle as part of onError methods
    there is also an overloaded method to accept a publisher
    we can have multiple timeouts. the closest one to the subscriber will take effect for the subscriber.
 */
@SuppressWarnings("unused")
public class Lec09Timeout {

    private static final Logger log = LoggerFactory.getLogger(Lec09Timeout.class);

    public static void main(String[] args) {

        var mono = getProductName()
                .log("first")
                .timeout(Duration.ofSeconds(1), getFallbackProductName()) //Fallback get subscribed only after timeout and cancellation of the main publisher
                .onErrorReturn("fallback");

        mono
                .log("second")
                .timeout(Duration.ofMillis(1120)) // will sometimes be triggered before the first timeout
                .subscribe(Util.subscriber());


        Util.sleepSeconds(2);

    }


    private static Mono<String> getProductName() {
        return Mono.fromSupplier(() -> "service" + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1500))
                //.log("producer1")
                ;
    }

    private static Mono<String> getFallbackProductName() {
        return Mono.fromSupplier(() -> "fallback" + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(100))
                //.log("producer2")
                ;
    }
}
