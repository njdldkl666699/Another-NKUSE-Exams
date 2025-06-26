package org.example.cdshop;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
@Slf4j
public class SoldCD extends CD {

	public int count;
	public SoldCD() {
		count=10;
	}
	synchronized public boolean sold(int num) {
		if(count>=num) {
			count-=num;
			log.info(
					new Date()+
					Thread.currentThread().getName()
					+this.cdName
					+"销售了"+num
					+"剩余"+count);
			return true;
		}
		else
		{
			log.info(new Date()+
			Thread.currentThread().getName()
			+this.cdName
			+"数量不足");
			return false;
		}
		
	}
	synchronized public void getin() {
		count=10;
		
		log.info(
				new Date()+
				Thread.currentThread().getName()
				+this.cdName
				+"进货");
		
	}
}
