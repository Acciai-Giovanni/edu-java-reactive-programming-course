package it.gacciai.sec12.assignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SlackRoom {

    private final String name;
    private final Sinks.Many<SlackMessage> sink;
    private final Flux<SlackMessage> flux;

    public SlackRoom(String name) {
        this.name = name;
        this.sink = Sinks.many().replay().all();
        this.flux = sink.asFlux();
    }

    public void addMember(SlackMember member) {
        flux.filter(message -> !message.sender().equals(member.getName()))
                .subscribe(member);

        member.setMessageConsumer(msg -> {
            this.sink.tryEmitNext(new SlackMessage(member.getName(), msg));
        });
    }
}

