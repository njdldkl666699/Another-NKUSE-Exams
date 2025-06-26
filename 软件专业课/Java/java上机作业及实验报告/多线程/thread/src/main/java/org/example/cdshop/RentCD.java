package org.example.cdshop;

import java.util.ArrayList;

public class RentCD extends CD {
    static int cdNum=1;
    public volatile boolean ifRented = false;
    public RentCD() {
        super(cdNum++);
    }
    synchronized public void changeRented() {
        ifRented=!ifRented;
    }
    synchronized public boolean tryRent() {
        if(!ifRented) {
            ifRented=true;
            return true;
        }
        return false;
    }
}
