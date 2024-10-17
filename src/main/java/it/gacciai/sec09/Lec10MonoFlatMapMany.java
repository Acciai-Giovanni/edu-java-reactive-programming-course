package it.gacciai.sec09;


import it.gacciai.common.Util;
import it.gacciai.sec09.applications.OrderService;
import it.gacciai.sec09.applications.UserService;

/*
    Sequential non-blocking IO calls!
    flatMap is used to flatten the inner publisher / to subscribe to the inner publisher
    Mono is supposed to be 1 item - what if the flatMap returns multiple items!?
 */
public class Lec10MonoFlatMapMany {

    public static void main(String[] args) {

        /*
            We have username
            get all user orders!
         */

        UserService.getUserId("mike ")
                   .flatMapMany(OrderService::getUserOrders)
                   .subscribe(Util.subscriber());


        Util.sleepSeconds(3);
    }

}
