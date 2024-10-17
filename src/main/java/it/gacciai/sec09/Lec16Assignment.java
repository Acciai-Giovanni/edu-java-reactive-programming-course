package it.gacciai.sec09;

import it.gacciai.sec09.applications.Order;

import java.util.List;

/*
    Get all users and build 1 object as shown here.
    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {}
 */
public class Lec16Assignment {

    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {
    }

    public static void main(String[] args) {


    }

}
