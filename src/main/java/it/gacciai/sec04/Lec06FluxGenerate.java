package it.gacciai.sec04;

import it.gacciai.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
    Flux generate
    - invokes the given lambda expression again and again based on downstream demand.
    - We can emit only one value at a time
    - will stop when complete method is invoked
    - will stop when error method is invoked
    - will stop downstream cancels
 */
public class Lec06FluxGenerate {

    private static final Logger log = LoggerFactory.getLogger(Lec06FluxGenerate.class);

    private static Long count = 0L;

    public static void main(String[] args) {

        Flux.generate(synchronousSync -> {
            log.info("invoked");
            synchronousSync.next(1);
        })
                .log()
                .take(4)
                .subscribe(Util.subscriber());


    }

}
