package Java_Reflect.proxy;

public class HelloServiceImp2 implements HelloService,DataAccess {

	@Override
	public void hello() {
		// TODO Auto-generated method stub
		System.out.println("second Version");
	}

	@Override
	public void goodbye() {
		// TODO Auto-generated method stub
		System.out.println("second Version");
	}

	@Override
	public Object read() {
		// TODO Auto-generated method stub
		System.out.println("read second Version");
		return "second Version";
	}

	@Override
	public void write(Object o) {
		// TODO Auto-generated method stub
		
	}

}
