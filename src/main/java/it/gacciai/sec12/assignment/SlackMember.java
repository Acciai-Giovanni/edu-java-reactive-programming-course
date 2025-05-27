package it.gacciai.sec12.assignment;

import it.gacciai.common.DefaultSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SlackMember implements Subscriber<SlackMessage> {

    private static final Logger log = LoggerFactory.getLogger(SlackMember.class);

    private final String name;
    private Consumer<String> messageConsumer;

    public SlackMember(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(SlackMessage s) {
        log.info(s.formatForDelivery(this.name));
    }

    @Override
    public void onError(Throwable t) {
        log.error("{} Error occurred", this.name, t);
    }

    @Override
    public void onComplete() {
        log.info("{} completed", this.name);
    }

    void setMessageConsumer(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void says(String message) {
        this.messageConsumer.accept(message);
    }
}
