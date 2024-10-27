package it.gacciai.sec10.assignment.groupby;

import it.gacciai.common.Util;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

public record PurchaseOrder(String item, String category, Integer price) {

    public static PurchaseOrder create() {
        var order = Util.faker().commerce();
        return new PurchaseOrder(
                order.productName(),
                order.department(),
                Util.faker().random().nextInt(10, 100)
        );
    }

    public PurchaseOrder increasePrice(int price) {
        return new PurchaseOrder(this.item, this.category, this.price + price);
    }

    public PurchaseOrder getFreeOrder() {
        return new PurchaseOrder(this.item, this.category, 0);
    }

}
