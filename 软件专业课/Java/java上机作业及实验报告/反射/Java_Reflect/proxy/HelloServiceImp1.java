package Java_Reflect.proxy;

public class HelloServiceImp1 implements HelloService,Comparable {

	@Override
	public void hello() {
		// TODO Auto-generated method stub
		System.out.println("hello first Version ");
	}

	@Override
	public void goodbye() {
		// TODO Auto-generated method stub
		System.out.println("good bye first Version");
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
