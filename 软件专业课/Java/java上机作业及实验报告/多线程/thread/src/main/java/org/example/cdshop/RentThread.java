package org.example.cdshop;


import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class RentThread extends Thread {
    private Vector<RentCD> rentCD;
    public RentThread(Vector<RentCD> rentCD) {
        super();
        this.rentCD = rentCD;
        this.setName(this.getName()+"租借线程");
    }
    @Override
    public void run() {
        //Random rand;
        while (true) {
            Random rand = new Random();
            int rentId = rand.nextInt(1, 10);
            RentCD wantedCd = rentCD.get(rentId);
            //synchronized (wantedCd){
            while (true) {
                if (!wantedCd.tryRent()) {
                    boolean ifwait = rand.nextBoolean();
                    if (ifwait) {
                        int waitTime = rand.nextInt(200);
                        log.info(new Date() + wantedCd.cdName + "已借出" + Thread.currentThread().getName() + "等待中");
                        synchronized (wantedCd){
                            try {
                                wantedCd.wait(waitTime);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    else {
                        log.info(new Date() + wantedCd.cdName + "已借出" + Thread.currentThread().getName() + "放弃");
                        break;
                    }
                }

                else {
                    int sleepTime = rand.nextInt(200, 300);
                    log.info(new Date() + Thread.currentThread().getName() + "租到了cd：" + wantedCd.cdName + "持有" + sleepTime + "ms");
                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info(new Date() + Thread.currentThread().getName() + "归还cd：" + wantedCd.cdName);
                    synchronized (wantedCd){
                        wantedCd.notify();
                        wantedCd.changeRented();
                    }
                    break;
                }
            }
            int sleepTime = rand.nextInt(200);
            log.info(
                    new Date() + Thread.currentThread().getName() + "睡眠时间" + sleepTime);
            try {
                this.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //}
    }
}
