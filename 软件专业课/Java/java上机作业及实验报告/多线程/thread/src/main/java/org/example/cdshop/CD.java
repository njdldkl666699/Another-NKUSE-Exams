package org.example.cdshop;

public class CD {

	static int id=0;
	public String ISBN;
	public String cdName;
	public CD() {
		ISBN="ISBN"+id;
		cdName="CD"+id;
		id++;
		
	}
	public CD(Integer id)
	{
		ISBN="ISBN"+id;
		cdName="CD"+id;
	}
}
