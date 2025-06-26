package org.example.threadexample;



public class VolatileTest extends Thread {

	 boolean state=true;
	public void run() {
		while(state) {
			
		}
	}
	public void stoprun(){
		state=false;
	}
	public static void main(String[] arg) throws InterruptedException {
		VolatileTest thread=new VolatileTest();
		thread.start();
		Thread.sleep(1000);
		thread.stoprun();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("System exit");
			}
		}));
	}
}
