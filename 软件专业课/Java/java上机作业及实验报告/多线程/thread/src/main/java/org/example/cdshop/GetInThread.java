package org.example.cdshop;

public class GetInThread extends Thread {

	SoldCD cd;
	GetInThread(SoldCD cd){
		this.cd=cd;
		this.setName(this.getName()+cd.cdName+"进货线程");
	}
	@Override
	public void run() {
		try {
			while(true) {
		synchronized(cd) {
			cd.wait(1000);
			cd.getin();			
		}
			}
		}
		catch(Exception e) {
			
		}
	}
}
