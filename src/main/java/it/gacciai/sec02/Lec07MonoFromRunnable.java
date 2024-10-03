package it.gacciai.sec02;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/*
    Emitting empty after some method invocation
 */
public class Lec07MonoFromRunnable {

    private static final Logger log = LoggerFactory.getLogger(Lec07MonoFromRunnable.class);

    public static void main(String[] args) {

        log.info("Getting product name");
        getProductName(1)
                .subscribe(Util.subscriber())
        ;
        log.info("Getting not existing product");

        getProductName(2)
                .subscribe(Util.subscriber())
        ;

        log.info("Main Done");
    }

    private static Mono<String> getProductName(int productId){
        if(productId == 1){
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }

        //return Mono.empty();

        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        log.info("notifying business on unavailable product {}", productId);

        try {
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
