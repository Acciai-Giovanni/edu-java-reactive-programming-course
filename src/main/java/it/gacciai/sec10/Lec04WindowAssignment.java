package it.gacciai.sec10;

import it.gacciai.common.Util;
import it.gacciai.sec10.assignment.window.FileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec04WindowAssignment {


    public static void main(String[] args) {

        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(Lec04WindowAssignment::processEvents)
                .subscribe();


        Util.sleepSeconds(60);

    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                   .map(i -> "event-" + (i + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        try {
            var fileService = FileService.openInstance();

            return flux.doOnNext(e -> fileService.write("*"))
                    .doOnComplete(fileService::writeln)
                    .then();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
