package Java_Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class Student{
	  public String stdname;
	  public Integer stdcode;
	  public Student(String stdname,Integer stdcode) {
		  this.stdname=stdname;
		  this.stdcode=stdcode;
	  }
	  public Student(String stdname) {
		  this.stdname=stdname;
		  this.stdcode=new Random().nextInt();
	  }
	  public void study(String lesson) {
		  System.out.println(stdname+" learning "+lesson);
	  }
	  public void setName(String stdname) {
		  this.stdname=stdname;
	  }  
	  public void doSomeHelp(Student s) {
		  System.out.println(stdname+" help "+s.stdname);
	  }
	}

public class Main {
	static Object createnew(String classname,Object[] para,Class[] paratype) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class c=Class.forName(classname);
		Constructor cons=c.getConstructor(paratype);
		return cons.newInstance(para);
		
	}
	static  Object methodCall(Object obj,String methodName,Class[] paratype,Object[] para) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class type=obj.getClass();
		Method method=type.getMethod(methodName, paratype);
		return method.invoke(obj, para);
	}
	static void getpara(String state,ArrayList<Object>para,ArrayList<Class> paratype
			,HashMap<String,Object> valist) throws Exception {
		if(state.trim().length()==0) {
			return;
		}
			
		String[] param=state.split(",");
		
		int parasize=param.length;
//		para=new Object[parasize];
//		paratype=new Class[parasize];
		int i=0;
		for(String par:param) {
			if(par.contains("\""))
			{
				paratype.add(String.class);
				para.add(par.trim().replace("\"", ""));
			}
			
			else
			{
				
				
				try {
				para.add(Integer.parseInt(par));
				paratype.add(Integer.class);
				}
				catch(Exception e) {
					Object o=valist.get(par.trim());
					if(o!=null) {
						paratype.add(o.getClass());
						para.add(o);
					}
					else
					{
						throw new Exception("Wrong Statement");
					}
					
				}
			}
			
			i++;
		}
	}

	static Object invo(String state,HashMap<String,Object> valist) throws Exception {
		int pos= state.indexOf(".");
		String objname=state.substring(0,pos).trim();
		Object value=valist.get(objname);
		if(value==null) {
			throw new Exception("Wrong Variable");
		}
		String methodname=state.substring(pos+1,state.length()).trim();
		 pos= methodname.indexOf("(");
		int pos2= methodname.indexOf(")");
		
		String method=methodname.substring(0,pos);
		String parastr=methodname.substring(pos+1,pos2);
		
		ArrayList<Object> ol=new ArrayList();
		ArrayList<Class> tl=new ArrayList();
		
		getpara(parastr,ol,tl,valist);
		Object [] para=new Object[ol.size()];
		ol.toArray(para);
		Class[] paratype=new Class[tl.size()];
		tl.toArray(paratype);
		try {
			return methodCall(value,method,paratype,para);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	public static void main(String [] arg) {
		HashMap<String,Object> va=new HashMap();
		Scanner scan=new Scanner(System.in);
		while(scan.hasNext()) {
			String stat=scan.nextLine();
			try {
			if(stat.contains("=")) {
				String[] stats=stat.split("=");
				String target=stats[0];
				String source=stats[1].trim();
				String typestr="";
				String valuename="";
				
				if(target.trim().contains(" ")) {
					typestr=target.trim().split(" ")[0];
					valuename=target.trim().replace(" ", "").replace(typestr, "");
					
					
				}
				else
					valuename=target.trim();
				
				if(source.length()>3&&source.substring(0,3).equals("new")) {
					source.substring(3,source.length());
					int pos=source.indexOf("(",3);
					int pos2=source.indexOf(")",3);
					String sourtypestr=source.substring(3,pos).trim();
					
					ArrayList<Object> ol=new ArrayList();
					ArrayList<Class> tl=new ArrayList();
					
					getpara(source.substring(pos+1,pos2),ol,tl,va);
					Object [] para=new Object[ol.size()];
					ol.toArray(para);
					Class[] paratype=new Class[tl.size()];
					tl.toArray(paratype);
					
					Object obj=createnew(sourtypestr,para,paratype);
					va.put(valuename, obj);						
				}
				else if(source.contains("."))
				{
					Object obj=invo(source,va);
					va.put(valuename, obj);		
				}
				else
				{
					Object obj =va.get(source);
					if(obj!=null) {
					va.put(valuename, obj);
					}
					else
					throw new Exception ("Wrong Variable");
				}
				
				
			}
			else
			{
				Object obj=invo(stat.trim(),va);
			}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
}
