package it.gacciai.sec06;

import it.gacciai.common.Util;
import it.gacciai.sec06.assignment.ExternalServiceClient;

public class Lec06Assignment {

    public static void main(String[] args) {

        var client = new ExternalServiceClient();
        var service = client.getOrders().publish().autoConnect(2);

        service.subscribe(Util.subscriber("Inventory"));
        service.subscribe(Util.subscriber("Revenue"));

    }

    //Order stream needs min 2 subscribers
    //order-service produces:

    //String Item
    //String Category
    //Integer Price
    //Integer Quantity

    //item:category:price:quantity

    //inventory service -> Keep track of the quantities per category (local memory with MAP) - Start with 500 items per category.
    //                     Emit every 2 seconds the whole warehouse stock levels to the warehouse manager
    //Revenue service -> Emit every 2 seconds the total revenue per category (local memory with MAP) -> to the CFO
}
