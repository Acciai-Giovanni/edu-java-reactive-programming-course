package it.gacciai.sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StockSubscriber<T> implements Subscriber<Long> {

    private static final Logger log = LoggerFactory.getLogger(StockSubscriber.class);
    private Subscription subscription;

    private final long startingBalance;
    private final long buyThreshold;
    private final long selloutThreshold;

    private long balance;
    private long stock;

    public StockSubscriber(long initialBalance, long buyThreshold, long selloutThreshold) {
        this.startingBalance = initialBalance;
        this.balance = initialBalance;
        this.buyThreshold = buyThreshold;
        this.selloutThreshold = selloutThreshold;
        this.stock = 0;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Long currentStockPrice) {

        if (currentStockPrice < buyThreshold && balance >= currentStockPrice) {
            log.info("Buying stock: {}", currentStockPrice);
            buyAt(currentStockPrice);
        } else if (currentStockPrice > selloutThreshold) {
            log.info("Selling all {} stock at: {}", stock, currentStockPrice);
            sellAllAt(currentStockPrice);
            log.info("Current balance: {} / Starting balance: {}", balance, startingBalance);
            subscription.cancel();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("onComplete balance: {}", balance);
        log.info("onComplete stock: {}", stock);
    }

    private void buyAt(long currentStockPrice) {
        stock++;
        balance -= currentStockPrice;
    }

    private void sellAllAt(long currentStockPrice) {
        balance += stock * currentStockPrice;
        stock = 0;
    }
}
