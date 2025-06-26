package Java_Reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
//  通过反射完成方法的动态调用
		String classname="proxy.HelloServiceImp2";
		Class helloimp=Class.forName(classname);
		Object obj=helloimp.newInstance();
		String methodname="hello";
		Method helloMethod=helloimp.getMethod(methodname, new Class[] {});
		//helloMethod.invoke(obj, new Object[] {});
		//通过代理完成方法的调用
		
		HelloService helloproxy=(HelloService)		
		Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), 
        new Class[] {HelloService.class,DataAccess.class},new DataAccessProxy(obj) );
		helloproxy.hello();
		helloproxy.goodbye();
		DataAccess dataaccess=(DataAccess)helloproxy;
		System.out.println(dataaccess.read());
		System.out.println(((DataAccess)obj).read());
		
		
	}

}
