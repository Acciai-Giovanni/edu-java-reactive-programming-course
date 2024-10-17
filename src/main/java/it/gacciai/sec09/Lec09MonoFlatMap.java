package it.gacciai.sec09;

import it.gacciai.common.Util;
import it.gacciai.sec09.applications.PaymentService;
import it.gacciai.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
 */
public class Lec09MonoFlatMap {

    public static void main(String[] args) {

        /*
            We have username.
            Get user account balance
         */

        UserService.getUserId("mike")
                .flatMap(PaymentService::getUserBalance)
                //.flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

//        Util.sleepSeconds(3);

    }

}
