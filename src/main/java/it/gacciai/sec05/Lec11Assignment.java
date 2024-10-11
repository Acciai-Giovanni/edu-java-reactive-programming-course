package it.gacciai.sec05;

import it.gacciai.common.Util;
import it.gacciai.sec05.assignment.ProductServiceImpl;

public class Lec11Assignment {

    public static void main(String[] args) {


        var service = new ProductServiceImpl();

        service.getProductNames()
                .subscribe(Util.subscriber());

    }

}
