package it.gacciai.sec10;

import it.gacciai.common.Util;
import it.gacciai.sec10.assignment.window.FileService;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec04WindowAssignment {


    public static void main(String[] args) {

        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(FileService::writeEvents)
                .subscribe();

        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                   .map(i -> "event-" + (i + 1));
    }

}
