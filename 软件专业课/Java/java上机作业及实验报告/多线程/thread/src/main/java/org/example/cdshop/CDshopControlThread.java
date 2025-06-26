package org.example.cdshop;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CDshopControlThread extends Thread{

	CDShop shop;

	public CDshopControlThread(CDShop shop) {
		super();
		this.shop = shop;
		this.setDaemon(true);
	}
	@Override
	public void run() {
		for(SoldCD cd:shop.soldcds) {
			new GetInThread(cd).start();
		}
		Random rand = new Random();
		int soldthreadcount=rand.nextInt(2,10);
		log.info("售卖和租借线程各有：{}个",soldthreadcount);
		for(int i=0;i<soldthreadcount;i++) {
			new SoldThread(shop.soldcds).start();
		}
		for(int i=0;i<soldthreadcount;i++) {
			new RentThread(shop.rentcds).start();
		}
    }
	
}
