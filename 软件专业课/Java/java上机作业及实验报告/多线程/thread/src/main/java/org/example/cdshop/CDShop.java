package org.example.cdshop;

import java.util.Vector;

public class CDShop {

	SoldCD[] soldcds=new SoldCD[10];
	Vector<RentCD> rentcds;
	CDShop() {
		rentcds=new Vector<>();
		for(int i=0;i<10;i++) {
			soldcds[i]=new SoldCD();
			rentcds.add(new RentCD());
		}

	}
	public static void main(String[] arg) throws InterruptedException {
		System.out.println("你好");
		new CDshopControlThread(new CDShop()).start();
		Thread.sleep(1000*120);
	}
}
