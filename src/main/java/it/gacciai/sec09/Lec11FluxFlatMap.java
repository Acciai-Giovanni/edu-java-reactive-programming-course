package it.gacciai.sec09;


import it.gacciai.common.Util;
import it.gacciai.sec09.applications.OrderService;
import it.gacciai.sec09.applications.User;
import it.gacciai.sec09.applications.UserService;

import java.time.Duration;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec11FluxFlatMap {

    public static void main(String[] args) {

        /*
            Get all the orders from order service!
         */

        UserService.getAllUsers()
                   .map(User::id)
                   .flatMap(OrderService::getUserOrders)
                   .subscribe(Util.subscriber());

        Util.sleepSeconds(5);

    }

}
