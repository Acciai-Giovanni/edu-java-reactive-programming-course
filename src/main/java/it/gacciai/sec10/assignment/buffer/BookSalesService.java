package it.gacciai.sec10.assignment.buffer;

import it.gacciai.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class BookSalesService {
    public record BookOrder (String genre, String title, Integer price) { }

    public Flux<BookOrder> book() {
        return Flux.interval(Duration.ofMillis(500))
                   .map(i -> new BookOrder(
                           Util.faker().book().genre(),
                           Util.faker().book().title(),
                           Util.faker().random().nextInt(5, 79)
                   ));
    }

}
