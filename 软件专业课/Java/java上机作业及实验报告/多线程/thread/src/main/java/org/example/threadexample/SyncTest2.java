package org.example.threadexample;

import java.util.stream.Stream;
class syncobject{
	int value=0;
}
public class SyncTest2 extends Thread {

	 static volatile syncobject state=new syncobject(); 
	public void run() {
		
		//while(state==1) {
		for(int i=0;i<1000;i++)
			//synchronized(state) 
			{
				state.value++;
			}
		//}
	}
	
	public static void main(String[] arg) throws InterruptedException {
		SyncTest2 thread=new SyncTest2();
		Stream.generate(()->{return new SyncTest2();})
		.limit(10)
		.forEach(e->{e.start();});
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("SyncTest2.state"+SyncTest2.state.value);
			}
		}));
	}
}
