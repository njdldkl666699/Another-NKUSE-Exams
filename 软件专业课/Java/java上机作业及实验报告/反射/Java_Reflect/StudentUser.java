package Java_Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
/*class Student {
	private String stdcode;
	public void setStdcode(String stdcode) {
		this.stdcode=stdcode;
	}
	public String toString() {
		return "Student:"+stdcode;
	}

}*/
public class StudentUser extends BaseUser {
	private String stdcode;
	 StudentUser(int id,String stdcode){
		this.BaseInt=id;
		this.stdcode=stdcode;		
	}
	public void setStdcode(String stdcode) {
		this.stdcode=stdcode;
	}
	public String toString() {
		return "Student:"+BaseInt+stdcode;
	}

	public static void printmethod(Object o) {
		Class c=o.getClass();
		Arrays.asList( c.getDeclaredConstructors()).forEach(e->{
			System.out.println(Arrays.asList(e.getParameterTypes()));
		});
		
	}
	public static Object cloneObject(Object o) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class c=o.getClass();
		
		//c.newInstance();
		Arrays.asList( c.getDeclaredConstructors()).forEach(e->{
			System.out.println(e.getName());
		});
		Constructor constructor=c.getDeclaredConstructors()[0];
		int size=constructor.getParameterTypes().length;
		Object[] para=new Object[size];
		ArrayList paravalue=new ArrayList();
		Arrays.asList( constructor.getParameterTypes()).forEach(e->{
			if(e!=Integer.class&&e!=int.class)
			try {
				paravalue.add(e.newInstance());
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			else
				paravalue.add(0);
		});
		
		Object newObject=constructor.newInstance(paravalue.toArray());
		
		Arrays.asList( c.getDeclaredFields()).forEach(field->{
			
			field.setAccessible(true);
				try {
					field.set(newObject, field.get(o));
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
		});
Arrays.asList( c.getFields()).forEach(field->{			
			
				try {
					field.set(newObject, field.get(o));
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
		});
		
		return newObject;
		
	}
	public static void methodinvoke(Object o,String methodname,Object[] param) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ArrayList<Class> types=new ArrayList();
		Class[] typearray=new Class[param.length];
		Arrays.asList(param).forEach(e->
		types.add(e.getClass()));
		
		Method m=o.getClass().getDeclaredMethod(methodname, types.toArray(typearray));
		m.invoke(o, param);
	}
	
	/*public static void main(String [] arg) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object o=new StudentUser(1,"test");
		//printmethod("String");
//		Object o2=new BaseUser();
		Object o2=cloneObject(o);
		System.out.println(o2.getClass().getSimpleName()+o2);
		methodinvoke(o2,"setStdcode",new Object[] {"new stdcode"});
		Object o3=new Student();
		methodinvoke(o3,"setStdcode",new Object[] {"new stdcode student"});
		System.out.println(o3.getClass().getSimpleName()+o3);
	}*/
}
