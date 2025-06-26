package Java_Reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralInvocationHandler implements InvocationHandler {

	private Object proxy;
	public static Logger l=Logger.getLogger("General proxy");
	public GeneralInvocationHandler(Object proxy) {
		this.proxy=proxy;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws MyProxyException {
		// TODO Auto-generated method stub
		
		Object result=null;
		try {
			l.log(Level.INFO, "begin invloke"+method.getName());
			 result=method.invoke(this.proxy, args);
			 l.log(Level.INFO, "after invloke"+method.getName());
				 
		}
		catch(Throwable t) {
			MyProxyException e=new MyProxyException(t.getMessage());
			e.initCause(t);
			throw e;
		}
		
			return result;
		
		
		
		
	}

}
