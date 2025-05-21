package it.gacciai.sec13;

import it.gacciai.common.Util;
import it.gacciai.sec13.client.ExternalServiceClient;
import org.slf4j.Logger;
import reactor.util.context.Context;

public class Lec04ContextRateLimiterDemo {

    public static final Logger log = org.slf4j.LoggerFactory.getLogger(Lec04ContextRateLimiterDemo.class);

    public static void main(String[] args) {
        var client = new ExternalServiceClient();

        for (int i = 0; i < 20; i++) {
            client.getBook()
                    .contextWrite(Context.of("user", "mike"))
                    .subscribe(Util.subscriber());
            Util.sleepSeconds(1);
        }
        Util.sleepSeconds(1);

    }

}
