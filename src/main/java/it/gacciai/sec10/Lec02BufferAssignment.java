package it.gacciai.sec10;

import it.gacciai.common.Util;
import it.gacciai.sec10.assignment.buffer.BookSalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec02BufferAssignment {

    private static final Logger log = LoggerFactory.getLogger(Lec02BufferAssignment.class);

//    private static final Map<String, Integer> revenue = new HashMap<>();

    public record BookOrderRevenue (Map<String, Integer> revenue) { }

    public static void main(String[] args) {

        var allowedCategories = Set.of(
                "Science fiction",
                "Fantasy",
                "Suspense/Thriller"
        );

        var salesService = new BookSalesService();

        salesService.book()
                //.log()
                .filter(o -> allowedCategories.contains(o.genre()))
                .buffer(Duration.ofSeconds(5))
                //.map(Lec02BufferAssignment::report)
                .map(Lec02BufferAssignment::reportByTeacher)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    //My own implementation
    private static BookOrderRevenue report (List<BookSalesService.BookOrder> order) {
        Map<String, Integer> revenue = new HashMap<>();

        order.iterator().forEachRemaining(o -> {
            int prevRevenue = revenue.getOrDefault(o.genre(), 0);
            revenue.put(o.genre(), prevRevenue + o.price());
        });

        return new BookOrderRevenue(revenue);
    }

    //Teacher's implementation
    private static BookOrderRevenue reportByTeacher(List<BookSalesService.BookOrder> orders) {
        var revenue = orders.stream()
                .collect(Collectors.groupingBy(
                        BookSalesService.BookOrder::genre,
                        Collectors.summingInt(BookSalesService.BookOrder::price)
                ));
        return new BookOrderRevenue(revenue);
    }

}
