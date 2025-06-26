package Java_Reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

public class DataAccessProxy implements InvocationHandler {
	static Logger l=Logger.getLogger(DataAccessProxy.class.getName());

	private Object proxied;
	DataAccessProxy(Object o){
		proxied=o;
	}
	DataAccessProxy(){
		proxied=null;
	}
	public Object getProxied() {
		return proxied;
	}

	public void setProxied(Object proxied) {
		this.proxied = proxied;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		// TODO Auto-generated method stub
		//Proxy pro=(Proxy)arg0;
		
		System.out.println("proxy method name:"+arg1.getName());
		System.out.println("arg0 proxy type:"+arg0.getClass());
		if(arg1.getName().contains("write")) {
			System.out.println("write");
			//l.info("write");
		}
		String message="";
		if(arg2!=null && arg2.length>0) {
			if(arg2[0].getClass()==int.class||arg2[0].getClass()==Integer.class) {
				arg2[0]=(Integer)(arg2[0])*2;
				message="double";
			}
		}
		Object result=arg1.invoke(proxied, arg2);
		if(arg1.getName().contains("read")&&result.getClass()==String.class) {
			result="by proxy: "+result;
		}
		//l.info(message);
		return result;
	}

}
